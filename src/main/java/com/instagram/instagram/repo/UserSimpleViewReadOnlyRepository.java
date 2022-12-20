package com.instagram.instagram.repo;

import com.instagram.instagram.models.view.UserSimpleView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSimpleViewReadOnlyRepository extends ReadOnlyRepository<UserSimpleView, Integer> {
}
