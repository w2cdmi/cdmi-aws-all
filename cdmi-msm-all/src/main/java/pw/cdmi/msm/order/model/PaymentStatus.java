package pw.cdmi.msm.order.model;

public enum PaymentStatus {
	PaySuccess,					//支付成功
	PayFail,					//支付失败
	Waitpay,					//等待支付
	ApplyRefund,				//申请退款
	Refunded;					//已退款
}
