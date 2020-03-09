package com.wzn.azblog.base.service;

import com.wzn.ablog.common.entity.Link;
import com.wzn.azblog.base.dao.LinkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LinkService {

    @Autowired
    private LinkDao linkDao;

    public Page<Link> list(int page,int limit){
        Page<Link> pageInfo = linkDao.findAll(PageRequest.of(page - 1, limit));
        return pageInfo;
    }

    public void add(Link link){
        linkDao.save(link);
    }

    public void del(String[] ids){
        for (String id: ids){
            linkDao.deleteById(id);
        }
    }

    public Link findById(String id) {
        Link link = linkDao.findById(id).get();
        return link;
    }

    public List<Link> search(String keywords){
        List<Link> list = linkDao.search(keywords);
        return list;
    }
}
