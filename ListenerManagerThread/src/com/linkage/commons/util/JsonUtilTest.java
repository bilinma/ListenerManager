package com.linkage.commons.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtilTest {
	public static Order mockOrder() {
		Order order = new Order();
		order.setChannelCode("channe123");
		order.setOrderContent("to install a new phone");
		order.setOrderId(1L);
		order.setOrderTypeId("09");
		order.setStaffCode("staff001");
		order.setAcceptDate(new Date());
		Customer cust = new Customer();
		cust.setAddress("中国台北市五一路");
		cust.setId(1L);
		cust.setName("赵鑫");
		order.addCustomer(cust);
		return order;
	}

	public static void main(String[] args) {
		testOrder();

	}

	private static void testOrder() {
		String orderJsonString = JsonUtil.getJsonString(mockOrder());
		System.out.println("orderJsonString:" + orderJsonString);

		Map<String, Class> map = new HashMap<String, Class>();
		map.put("customers", Customer.class);
		Order order = (Order) JsonUtil.getObject(orderJsonString, Order.class,
				map);
		System.out.println("order content:" + order.getOrderContent());
		System.out.println("order content:" + order.getAcceptDate());
		System.out.println("order.customer.address:"
				+ order.getCustomers().get(0).getAddress());

	}

	public static class Customer implements Serializable {
		private static final long serialVersionUID = 1L;
		private long id;
		private String name;
		private String address;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
	}

	public static class Order implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private long orderId;
		private String orderTypeId;
		private String staffCode;
		private String channelCode;
		private String orderContent;
		private Date acceptDate;
		private List<Customer> customers;

		public Order() {
			customers = new ArrayList<Customer>();
		}

		public void addCustomer(Customer cust) {
			if (this.customers == null)
				customers = new ArrayList<Customer>();
			this.customers.add(cust);
		}

		public List<Customer> getCustomers() {
			return customers;
		}

		public void setCustomers(List<Customer> customers) {
			this.customers = customers;
		}

		public long getOrderId() {
			return orderId;
		}

		public Date getAcceptDate() {
			return acceptDate;
		}

		public void setAcceptDate(Date acceptDate) {
			this.acceptDate = acceptDate;
		}

		public void setOrderId(long orderId) {
			this.orderId = orderId;
		}

		public String getOrderTypeId() {
			return orderTypeId;
		}

		public void setOrderTypeId(String orderTypeId) {
			this.orderTypeId = orderTypeId;
		}

		public String getStaffCode() {
			return staffCode;
		}

		public void setStaffCode(String staffCode) {
			this.staffCode = staffCode;
		}

		public String getChannelCode() {
			return channelCode;
		}

		public void setChannelCode(String channelCode) {
			this.channelCode = channelCode;
		}

		public String getOrderContent() {
			return orderContent;
		}

		public void setOrderContent(String orderContent) {
			this.orderContent = orderContent;
		}
	}
}
