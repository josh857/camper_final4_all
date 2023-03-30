package cn.tedu.camper.portal.paypal.CaptureIntentExamples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.tedu.camper.portal.model.Shopcar;
import cn.tedu.camper.portal.model.User;
import cn.tedu.camper.portal.paypal.PayPalClient;
import org.json.JSONObject;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Money;
import com.paypal.orders.Name;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.orders.ShippingDetail;

public class CreateOrder extends PayPalClient {

	/**
	 * Method to generate sample create order body with <b>CAPTURE</b> intent
	 *
	 * @return OrderRequest with created order request
	 */
	private OrderRequest buildRequestBody(List<Shopcar> list,User user,String address) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("CAPTURE");

		ApplicationContext applicationContext = new ApplicationContext().brandName("EXAMPLE INC").landingPage("BILLING")
				.cancelUrl("http://35.79.221.211:8030/index.html").returnUrl("http://35.79.221.211:8030/payedshopList.html").userAction("CONTINUE")
				.shippingPreference("SET_PROVIDED_ADDRESS");
		orderRequest.applicationContext(applicationContext);

		List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
		//此為分隔地址
		String arr[] = address.split(",");
		String city = arr[0];
		String range = arr[1];
		String num = arr[2];
		String add = arr[3];
		String floor = arr[4];
		//此為算出所有購物車內商品的總額
		Integer total = 0;
		for(Shopcar shopcar:list){
			total+=shopcar.getTotal();
		}
			PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("PUHF")
					.description("Sporting Goods").customId("CUST-HighFashions").softDescriptor("HighFashions")
					.amountWithBreakdown(new AmountWithBreakdown().currencyCode("TWD").value(total.toString())
							.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("TWD").value(total.toString()))
//								.shipping(new Money().currencyCode("USD").value("20.00"))
//								.handling(new Money().currencyCode("USD").value("10.00"))
//								.taxTotal(new Money().currencyCode("USD").value("20.00"))
//								.shippingDiscount(new Money().currencyCode("USD").value("10.00"))
							))

					.items(new ArrayList<Item>() {
						{

							for(Shopcar shopcar:list) {
								add(new Item().name(shopcar.getTitle()).description(shopcar.getPrice().toString())//.sku("sku01")
										.unitAmount(new Money().currencyCode("TWD").value(shopcar.getPrice().toString()))
										.quantity(shopcar.getQuantity().toString()));
//								.tax(new Money().currencyCode("USD").value("10.00"))
//								.category("PHYSICAL_GOODS"));
							}
						}
					}
		)
					.shippingDetail(new ShippingDetail().name(new Name().fullName(user.getNickname()))
							.addressPortable(new AddressPortable().addressLine1(add).addressLine2(floor)
									.adminArea2(city).adminArea1(range).postalCode(num).countryCode("TW")));
			purchaseUnitRequests.add(purchaseUnitRequest);
			orderRequest.purchaseUnits(purchaseUnitRequests);
			return orderRequest;

	}

	/**
	 * Method to create order
	 *
	 * @param debug true = print response data
	 * @return HttpResponse<Order> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	public HttpResponse<Order> createOrder(boolean debug , List<Shopcar>list, User user,String address) throws IOException {
		OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer","return=representation");
		request.requestBody(buildRequestBody(list,user,address));
		HttpResponse<Order> response = client().execute(request);
		if (debug) {
			if (response.statusCode() == 201) {
				System.out.println("Status Code: " + response.statusCode());
				System.out.println("Status: " + response.result().status());
				System.out.println("Order ID: " + response.result().id());
				System.out.println("Intent: " + response.result().checkoutPaymentIntent());
				System.out.println("Links: ");
				for (LinkDescription link : response.result().links()) {
					System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
				}
				System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
						+ " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
				System.out.println("Full response body:");
				System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
			}
		}
		return response;
	}

	/**
	 * This is the driver function which invokes the createOrder function to create
	 * an sample order.
	 */
	public static void main(String args[]) {
//		try {
//			new CreateOrder().createOrder(true);
//		} catch (com.paypal.http.exceptions.HttpException e) {
//			System.out.println(e.getLocalizedMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
