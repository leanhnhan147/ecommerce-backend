package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.GroupPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupPermissionRepository extends JpaRepository<GroupPermission, Long>, JpaSpecificationExecutor<GroupPermission> {
}
