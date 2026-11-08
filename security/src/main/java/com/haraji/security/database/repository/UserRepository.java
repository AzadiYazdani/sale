
package com.haraji.security.database.repository;

import com.haraji.security.constant.RoleType;
import com.haraji.security.database.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
        SELECT u
        FROM UserEntity u
        LEFT JOIN u.person p
        WHERE
            LOWER(COALESCE(p.firstName, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(p.lastName, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(CONCAT(
                COALESCE(p.firstName, ''),
                ' ',
                COALESCE(p.lastName, '')
            )) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(CONCAT(
                COALESCE(p.lastName, ''),
                ' ',
                COALESCE(p.firstName, '')
            )) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR COALESCE(p.nationalCode, '') LIKE CONCAT('%', :keyword, '%')
            OR EXISTS (
                SELECT ui.id
                FROM UserIdentifierEntity ui
                WHERE ui.user = u
                  AND ui.enabled = true
                  AND LOWER(ui.value) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
        """)
    Page<UserEntity> searchUsers(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    long countByRoleAndEnabledTrueAndLockedFalse(RoleType role);

}
