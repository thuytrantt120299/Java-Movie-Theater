package com.vn.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vn.entity.Type;
import com.vn.repository.TypeRepository;

@SpringBootTest
public class TypeTest {
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Test
	public void addType() {
		List<Type> allTypes = Arrays.asList(
		new Type(1,"Hanh dong"),
		new Type(2,"Hai huoc"),
		new Type(3,"Lang man"),
		new Type(4,"Tinh cam"),
		new Type(5,"Vien tuong"),
		new Type(6,"Chien tranh"),
		new Type(7,"Kiem hiep"),
		new Type(8,"Am nhac"),
		new Type(9,"Kinh di"),
		new Type(10,"Phieu luu"),
		new Type(11,"Tam ly 18+"),
		new Type(12,"Hoat hinh")
				);
		
		typeRepository.saveAll(allTypes);
			
		assertThat(allTypes.size()).isEqualTo(12);
		
//		TypeDTO type1 = new TypeDTO("Hanh dong");
//		typeServiceImpl.saveType(type1);
//		TypeDTO type2 = new TypeDTO("Hai huoc");
//		typeServiceImpl.saveType(type2);
//		TypeDTO type3 = new TypeDTO("Lang man");
//		typeServiceImpl.saveType(type3);
//		TypeDTO type4 = new TypeDTO("Tinh cam");
//		typeServiceImpl.saveType(type4);
//		TypeDTO type5 = new TypeDTO("Chien tranh");
//		typeServiceImpl.saveType(type5);
//		TypeDTO type6 = new TypeDTO("Kiem hiep");
//		typeServiceImpl.saveType(type6);
//		TypeDTO type7 = new TypeDTO("Am nhac");
//		typeServiceImpl.saveType(type7);
//		TypeDTO type8 = new TypeDTO("Kinh di");
//		typeServiceImpl.saveType(type8);
//		TypeDTO type9 = new TypeDTO("Phieu luu");
//		typeServiceImpl.saveType(type9);
//		TypeDTO type10 = new TypeDTO("Tam ly 18+");
//		typeServiceImpl.saveType(type10);
//		TypeDTO type11 = new TypeDTO("Hoat hinh");
//		typeServiceImpl.saveType(type11);
//		TypeDTO type12 = new TypeDTO("Vien tuong");
//		typeServiceImpl.saveType(type12);
	}
}
