package com.Capstone.Capstone_Server.service.generator;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

//YYYYMMDD
public class DateGenerator implements IdentifierGenerator{
	public Serializable generate(SharedSessionContractImplementor session, Object obj) {
		String NowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return NowDate;
	}
	

}
