package com.ht.commons.support.tree;


import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.ht.commons.support.tree.entity.TreeEntity;

public class TreeBuilder {
    List<TreeEntity> nodes = new ArrayList<TreeEntity>();

    public TreeBuilder(List<TreeEntity> nodes) {
        super();
        this.nodes = nodes;
    }

    /**
     * 构建JSON树形结构
     *
     * @return
     */

    public String buildJsonTree() {
        List<TreeEntity> nodeTree = buildTree();
        JSONArray jsonArray = JSONArray.fromObject( nodeTree );
        return jsonArray.toString();
    }

    /**
     * 构建树形结构
     *
     * @return
     */
    public List<TreeEntity> buildTree() {
        List<TreeEntity> treeNodes = new ArrayList<TreeEntity>();
        List<TreeEntity> rootNodes = getRootNodes();
        for (TreeEntity rootNode : rootNodes) {

            buildChildNodes( rootNode );

            treeNodes.add( rootNode );

        }
        return treeNodes;
    }


    /**
     * 递归子节点
     *
     * @param node
     */

    public void buildChildNodes(TreeEntity node) {

        List<TreeEntity> children = getChildNodes( node );

        if (!children.isEmpty()) {

            for (TreeEntity child : children) {

                buildChildNodes( child );

            }

            node.setChildren( children );

        }

    }

    /**
     * 获取父节点下所有的子节点
     *
     * @param pnode
     * @param pnode
     * @return
     */

    public List<TreeEntity> getChildNodes(TreeEntity pnode) {

        List<TreeEntity> childNodes = new ArrayList<TreeEntity>();

        for (TreeEntity n : nodes) {

            if (pnode.getId().equals( n.getPid() )) {

                childNodes.add( n );

            }

        }

        return childNodes;

    }

    /**
     * 判断是否为根节点
     *
     * @param node
     * @param node
     * @return
     */

    public boolean rootNode(TreeEntity node) {

        boolean isRootNode = false;

        for (TreeEntity n : nodes) {

            if (ObjectUtils.equals(0, node.getPid()) || node.getPid()==null) {

                isRootNode = true;

                break;

            }

        }

        return isRootNode;

    }


    /**
     * 获取集合中所有的根节点
     *
     * @return
     */

    public List<TreeEntity> getRootNodes() {

        List<TreeEntity> rootNodes = new ArrayList<TreeEntity>();

        for (TreeEntity n : nodes) {

            if (rootNode( n )) {

                rootNodes.add( n );

            }

        }

        return rootNodes;

    }


    public static void main(String[] args) {

 

        List<TreeEntity>nodes = new ArrayList<TreeEntity>();

     /*   Citytree p1 = new Citytree("01", "","01", "");

        Citytree p6 = new Citytree("02", "","02", "");

        Citytree p7 = new Citytree("0201", "02","0201", "");

        Citytree p2 = new Citytree("0101", "01","0101", "");

        Citytree p3 = new Citytree("0102", "01","0102", "");

        Citytree p4 = new Citytree("010101", "0101","010101", "");

        Citytree p5 = new Citytree("010102", "0101","010102", "");

        Citytree p8 = new Citytree("03", "","03", "");

        nodes.add(p1);

        nodes.add(p2);

        nodes.add(p3);

        nodes.add(p4);

        nodes.add(p5);

        nodes.add(p6);

        nodes.add(p7);

        nodes.add(p8);*/


        TreeBuilder treeBuilder = new TreeBuilder(nodes);

        System.out.println(treeBuilder.buildJsonTree());

    }


}