package oss.airtel.dao.copper;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ResyncNAdaoImpl implements ResyncNAdao {

	@Autowired
    private EntityManager em;
	
	@Override
	public String getResyncCount(String dsl_id) {
		try 
		{
			Query NativeQuery = em.createNativeQuery("select RESYNCCOUNT from na_data where customer_label='"+dsl_id+"'");
			Object singleResult = NativeQuery.getSingleResult();
			String resync = singleResult.toString();
			return resync;
		} 
		catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Data Fetch Exception in dao: "+e.getMessage());
			return "NA";
		}
	}

}
