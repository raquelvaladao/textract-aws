package com.textract.api.domain.repositories;

import com.textract.api.domain.models.Identity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, String> {
}
