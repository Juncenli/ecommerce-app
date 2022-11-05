package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    @Query("SELECT u FROM User u  WHERE u.email = :email")
    // @Param("email") -> :email, bind data to query
    public User getUserByEmail(@Param("email") String email);

    public Long countById(Integer id);

    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

    // %?1% is a placeholder for keyword -> 选择第一个作为参数
    // overloading -> public Page<User> findAll(Pageable pageable) can still be used.
    // CONCAT is used for forming a new string -> and we use 'like' clause to find an entity which we can use it to form a new string which keyword is like -> keyword is a substring of the new string.
    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ',"
            + " u.lastName) LIKE %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);
}
