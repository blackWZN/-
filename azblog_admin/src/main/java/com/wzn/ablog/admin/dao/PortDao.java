package com.wzn.ablog.admin.dao;

import com.wzn.ablog.admin.entity.Port;
import com.wzn.ablog.admin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortDao extends JpaRepository<Port,String>, JpaSpecificationExecutor<Port> {


}
