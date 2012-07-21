package org.codebone.framework.generic;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.codebone.framework.BaseModel;
import org.codebone.framework.SuccessModel;
import org.springframework.stereotype.Service;


@Service
public abstract class AbstractService<M extends AbstractModel> {
	
	protected static final Logger logger = Logger.getLogger("service");
	
	public abstract AbstractDao getDao();
	
	public BaseModel create (M m){
		logger.info("create model " + m);
		getDao().create(m);
		return new SuccessModel();
	}
	
	public BaseModel read (String key){
		logger.info("read model by" + key);
		M m = (M) getDao().read(key);
		return new SuccessModel(m);
	}
	
	public BaseModel listAll(){
		logger.info("read model list All");
		List<M> list = getDao().listAll();
		return new SuccessModel(list);
	}
	
	public BaseModel list(){
		logger.info("read model list");
		List<M> list = getDao().list();
		return new SuccessModel(list);
	}
	
	public BaseModel list(int page){
		page=page-1;
		logger.info("read model list");
		List<M> list = getDao().list(page, 20+1);
		if(list.size()==21){
			list.remove(20);
			return new SuccessModel(list, true, getDao().count());
		}
		return new SuccessModel(list, false, getDao().count());
	}
	
	public BaseModel list(int page, int row){
		page=page-1;
		logger.info("read model list");
		List<M> list = getDao().list(page, row+1);
		if(list.size()==row+1){
			list.remove(row);
			return new SuccessModel(list, true, getDao().count());
		}
		return new SuccessModel(list, false, getDao().count());
	}
	
	public BaseModel search(String property, String keyword, int page){
		page=page-1;
		logger.info("read model list");
		List<M> list = getDao().search(property, keyword, page, 20+1);
		if(list.size()==21){
			list.remove(20);
			return new SuccessModel(list, true, getDao().count());
		}
		return new SuccessModel(list, false, getDao().count());
	}
	
	public BaseModel update (M m){
		logger.info("update model " + m);
		M returnModel = (M) getDao().update(m);
		return new SuccessModel(returnModel);
	}
	
	public BaseModel merge (M m){
		logger.info("merge model " + m);
		getDao().merge(m);
		return new SuccessModel();
	}
	
	public BaseModel delete (M m){
		logger.info("delete model " + m);
		getDao().delete(m);
		return new SuccessModel();
	}
	
	public BaseModel evict (M m){
		logger.info("evict model " + m);
		getDao().evict(m);
		return new SuccessModel();
	}

}
