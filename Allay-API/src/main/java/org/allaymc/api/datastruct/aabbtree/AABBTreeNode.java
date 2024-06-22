package org.allaymc.api.datastruct.aabbtree;

import lombok.Getter;
import lombok.Setter;
import org.joml.primitives.AABBf;

/**
 * Allay Project 2023/7/30
 *
 * @author daoge_cmd
 */
@Getter
@Setter
public final class AABBTreeNode<E extends HasAABB> {
    public static final int INVALID_NODE_INDEX = -1;

    public static final int LEFT_CHILD = 0;
    public static final int RIGHT_CHILD = 1;

    private static final int MAX_NUM_OF_CHILDREN_PER_NODE = 2;

    private final int[] children = new int[MAX_NUM_OF_CHILDREN_PER_NODE];
    private final AABBf aabb = new AABBf();

    private int parent = INVALID_NODE_INDEX;
    private int index = INVALID_NODE_INDEX;
    private int height;
    private E data;

    public AABBTreeNode() {
        assignChildren(INVALID_NODE_INDEX, INVALID_NODE_INDEX);
    }

    public boolean isLeaf() {
        return getLeftChild() == INVALID_NODE_INDEX;
    }

    void replaceChild(int childIndexToReplace, int replacement) {
        if (getLeftChild() == childIndexToReplace) {
            assignChild(LEFT_CHILD, replacement);
        } else if (getRightChild() == childIndexToReplace) {
            assignChild(RIGHT_CHILD, replacement);
        }
    }

    void assignChild(int whichChild, int childIndex) {
        children[whichChild] = childIndex;
    }

    public void assignChildren(int childA, int childB) {
        assignChild(LEFT_CHILD, childA);
        assignChild(RIGHT_CHILD, childB);
    }

    void computeAABBWithMargin(float margin) {
        if (data == null) return;
        AABBf dataAABB = data.copyOffsetAABBTo(aabb);
        aabb.setMin(dataAABB.minX - margin, dataAABB.minY - margin, dataAABB.minZ - margin);
        aabb.setMax(dataAABB.maxX + margin, dataAABB.maxY + margin, dataAABB.maxZ + margin);
    }

    public void resetForReuse() {
        assignChildren(INVALID_NODE_INDEX, INVALID_NODE_INDEX);

        aabb.setMin(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        aabb.setMax(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

        this.parent = INVALID_NODE_INDEX;
        this.height = 0;
        this.data = null;
    }

    public int getLeftChild() {
        return children[LEFT_CHILD];
    }

    public int getRightChild() {
        return children[RIGHT_CHILD];
    }
}
