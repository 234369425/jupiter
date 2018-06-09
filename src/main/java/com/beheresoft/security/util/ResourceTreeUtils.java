package com.beheresoft.security.util;

import com.beheresoft.security.enums.ResourceType;
import com.beheresoft.security.pojo.Resource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Aladi
 * @date 2018-06-08 10:50:24
 */
@Component
public class ResourceTreeUtils {

    @Getter
    @Setter
    @NoArgsConstructor
    public class TreeNode {
        private Long id;
        private ResourceType type;
        private String key;
        private String uri;
        private String name;
        private Boolean leaf;
        private List<TreeNode> nodes;

        public TreeNode(Resource resource) {
            parseResource(resource);
        }

        public void setNodes(List<TreeNode> nodes) {
            if (this.nodes != null) {
                this.nodes.addAll(nodes);
            } else {
                this.nodes = nodes;
            }
        }

        public void parseResource(Resource resource) {
            this.id = resource.getResourceId();
            this.type = resource.getType();
            this.key = resource.getPermKey();
            this.uri = resource.getUri();
            this.name = resource.getName();
        }

        public void selfToChild() {
            if (this.id == Long.MIN_VALUE) {
                return;
            }
            TreeNode newTreeNode = new TreeNode();
            newTreeNode.id = this.id;
            newTreeNode.type = this.type;
            newTreeNode.key = this.key;
            newTreeNode.uri = this.uri;
            newTreeNode.name = this.name;
            this.id = Long.MIN_VALUE;
            this.type = ResourceType.ROOT;
            this.key = null;
            this.uri = null;
            this.name = null;
            this.addChild(newTreeNode);
        }

        public void addChild(TreeNode node) {
            if (nodes == null) {
                nodes = new LinkedList<>();
            }
            nodes.add(node);
        }
    }

    public TreeNode resourceTree(List<Resource> resources) {
        if (resources == null) {
            return new TreeNode();
        }

        final TreeNode root = new TreeNode();
        final Map<Long, List<TreeNode>> map = new HashMap<>(5);
        resources.forEach(resource -> {
            switch (resource.getType()) {
                case ROOT:
                case FOLDER:
                case MENU:
                    if (resource.getParentId() == null) {
                        if (root.getId() == null) {
                            root.parseResource(resource);
                        } else {
                            root.selfToChild();
                            root.addChild(new TreeNode(resource));
                        }
                    }
                    map.computeIfAbsent(resource.getParentId(), v -> new ArrayList<>()).add(new TreeNode(resource));
                default:
            }
        });
        if (!map.isEmpty()) {
            buildTree(root, map);
        }
        return root;
    }

    private void buildTree(TreeNode ref, Map<Long, List<TreeNode>> map) {
        List<TreeNode> children = map.get(ref.getId());
        if (children == null && ref.getId() != Long.MIN_VALUE) {
            ref.setLeaf(Boolean.TRUE);
            return;
        }
        if (children != null) {
            ref.setNodes(children);
        }
        if (ref.getNodes() != null) {
            ref.getNodes().forEach(node -> {
                buildTree(node, map);
            });
        }
    }


}
