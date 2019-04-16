package DaoTest.dao;

import org.mockito.ArgumentMatcher;


public class IsReiziger implements ArgumentMatcher<Reiziger>{

	public boolean matches(Reiziger r1) {
		if(r1 instanceof Reiziger) {
			return true;
		}
		
		return false;
	}

}//test2
