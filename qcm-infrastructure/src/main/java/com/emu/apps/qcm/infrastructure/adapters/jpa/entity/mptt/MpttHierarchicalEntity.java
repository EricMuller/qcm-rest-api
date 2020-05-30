package com.emu.apps.qcm.infrastructure.adapters.jpa.entity.mptt;


public interface MpttHierarchicalEntity<T extends MpttHierarchicalEntity <T>> {

    Long getId();

    /**
     * This property would allow us to have multiple hierarchies
     * in the same entity without the need to have a share root.
     * <p>
     * Instead of a tree, we would have a forest. Each forest
     * identified with a unique hierarchy id. Thus the indexing
     * between hierarchies can be independent.
     *
     * @param userId the id of the hierarchy
     */

    void setUserId(String userId);

    String getUserId();

    void setLft(Long lft);

    Long getLft();

    void setRgt(Long rgt);

    Long getRgt();

    Integer getLevel();

    void setLevel(Integer level);

}
