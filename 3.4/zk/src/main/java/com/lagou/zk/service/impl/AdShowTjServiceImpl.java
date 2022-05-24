package com.lagou.zk.service.impl;

import com.lagou.zk.dao.AdShowTjDao;
import com.lagou.zk.entity.AdShowTj;
import com.lagou.zk.service.AdShowTjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdShowTjServiceImpl implements AdShowTjService {

    @Autowired
    private AdShowTjDao dao;
    @Override
    public void save(AdShowTj tj) {
        dao.save(tj);
    }
}
