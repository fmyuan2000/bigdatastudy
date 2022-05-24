package com.lagou.zk.dao;

import com.lagou.zk.entity.AdShowTj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface AdShowTjDao extends JpaRepository<AdShowTj,Integer>, Serializable {
}
