package com.instagram.instagram.repo;

import com.instagram.instagram.models.view.PostSimpleView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostSimpleViewReadOnlyRepository extends JpaRepository<PostSimpleView, Integer> {
}
