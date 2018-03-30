using System;

namespace art_emotherearth_ws {
    class Class1 {
        [STAThread]
        static void Main(string[] args) {
            localhost.OrderInfoService orderInfo = 
                new localhost.OrderInfoService();
            Console.WriteLine("Order status for order # 1 is " + 
                orderInfo.getOrderStatus(1));
            Console.WriteLine("Shipping status for order # 1 is " + 
                orderInfo.getShippingStatus(1));
        }
    }
}
