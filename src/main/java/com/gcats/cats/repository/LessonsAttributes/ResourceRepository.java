package com.gcats.cats.repository.LessonsAttributes;

import com.gcats.cats.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("resourceRepository")
public interface ResourceRepository  extends JpaRepository<Resource, Long> {
}
