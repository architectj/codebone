package org.codebone.security.manager;

import org.codebone.framework.generic.AbstractDao;
import org.springframework.stereotype.Repository;


@Repository
public class ManagerDao extends AbstractDao<Manager>{

	@Override
	protected Class<Manager> getEntityClass() {
		return Manager.class;
	}
}
