package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories.mptt;

public interface MpttExceptions {

    class HierarchyIdAlreadySetException extends Exception {
    }

    class HierarchyIdNotSetException extends Exception {
    }

    class HierarchyRootDoesNotExistException extends Exception {
    }

    class HierarchyRootExistsException extends Exception {
    }

    class NotADescendantException extends Exception {
    }

    class NotInTheSameHierarchyException extends Exception {
    }
}
