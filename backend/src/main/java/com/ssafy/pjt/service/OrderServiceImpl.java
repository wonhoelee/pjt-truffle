package com.ssafy.pjt.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt.dao.OrderDao;
import com.ssafy.pjt.dto.OrderDto;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	public boolean completePayment(OrderDto orderDto) throws SQLException {
		return orderDao.completePayment(orderDto) == 1;
	}

	@Override
	public OrderDto selectOrderByEventId(int event_id) throws SQLException {
		return orderDao.selectOrderByEventId(event_id);
	}

	@Override
	public boolean deleteOrderByEventId(int event_id) throws SQLException {
		return orderDao.deleteOrderByEventId(event_id) == 1;
	}

}
