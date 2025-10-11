package com.eCommerce.utils;

import com.eCommerce.entity.ProductOrder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class CommonUtils {

    private JavaMailSender javaMailSender;

    public CommonUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // Send reset password email
    public boolean sendMail(String url, String email) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("yaduvanshimrityunjay461@gmail.com", "Shopping Cart");
        helper.setTo(email);

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + url + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject("Reset Your Password");
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
    }

    // Send order confirmation email
    public boolean sendOrderConfirmationMail(ProductOrder productOrder) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("yaduvanshimrityunjay461@gmail.com", "Shopping Cart");
        helper.setTo(productOrder.getOrderAddress().getEmail());

        String content = "<p>Hello,</p>"
                + "<p>Your order has been placed successfully.</p>"

                + "<p>Order ID: " + productOrder.getOrderId() + "</p>"
                + "<p>Product: " + productOrder.getProduct().getTitle() + "</p>"
                + "<p>Quantity: " + productOrder.getQuantity() + "</p>"
                + "<p>Price: $" + productOrder.getPrice() + "</p>"
                + "<p>Status: " + productOrder.getStatus() + "</p>"
                + "<br>"
                + "<p>We will notify you once your order is shipped.</p>"

                + "<p>Click the link below to view your order details:</p>"
                + "<br>"
                + "<p>Thank you for shopping with us!</p>";

        helper.setSubject("Order Confirmation");
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
    }


    // Send order summary email for multiple orders (cart checkout)
    public boolean sendOrderSummaryMail(List<ProductOrder> orders) throws MessagingException, UnsupportedEncodingException {

        if (orders == null || orders.isEmpty()) return false;

        ProductOrder first = orders.get(0);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("yaduvanshimrityunjay461@gmail.com", "Shopping Cart");
        helper.setTo(first.getOrderAddress().getEmail());
        helper.setSubject("Order Confirmation!");

        StringBuilder rows = new StringBuilder();
        double itemsTotal = 0.0;
        for (ProductOrder po : orders) {
            double line = po.getQuantity() * po.getPrice();
            itemsTotal += line;
            rows.append("""
          <tr>
            <td>%s</td>
            <td style="text-align:center;">%d</td>
            <td style="text-align:right;">$%.2f</td>
            <td style="text-align:right;">$%.2f</td>
          </tr>
        """.formatted(
                    esc(po.getProduct().getTitle()),
                    po.getQuantity(),
                    po.getPrice(),
                    line
            ));
        }

        double deliveryFee = 250; // set your real values if any
        double tax = 100;         // set your real values if any
        double grand = itemsTotal + deliveryFee + tax;

        // If you don't have a checkout/batch id, show first orderId or "N/A"
        String checkoutRef = first.getOrderId(); // or a dedicated checkoutId if you add one

        String content = """
      <div style="font-family: Arial, sans-serif; color:#333;">
        <p>Hello %s,</p>
        <p>Your order has been placed successfully.</p>
        <p><b>Checkout Ref:</b> %s</p>
        <table cellpadding="8" cellspacing="0" width="100%%" style="border-collapse:collapse;">
          <thead>
            <tr style="background:#f2f2f2;">
              <th align="left">Product</th><th align="center">Qty</th>
              <th align="right">Price</th><th align="right">Total</th>
            </tr>
          </thead>
          <tbody>%s</tbody>
          <tfoot>
            <tr><td colspan="3" align="right"><b>Items Total:</b></td><td align="right"><b>$%.2f</b></td></tr>
            <tr><td colspan="3" align="right">Delivery:</td><td align="right">$%.2f</td></tr>
            <tr><td colspan="3" align="right">Tax:</td><td align="right">$%.2f</td></tr>
            <tr><td colspan="3" align="right"><b>Grand Total:</b></td><td align="right"><b>$%.2f</b></td></tr>
          </tfoot>
        </table>
        <h4>Shipping Address</h4>
        <p>%s %s<br>%s<br>%s, %s - %s<br>Phone: %s</p>
        <p>We will notify you once your order is shipped.</p>
        <p>Thank you for shopping with us!</p>
      </div>
    """.formatted(
                esc(first.getOrderAddress().getFirstName()),   // %s (greeting)
                esc(checkoutRef),                               // %s (checkout ref)
                rows.toString(),                                // %s (table rows)
                itemsTotal, deliveryFee, tax, grand,            // %.2f, %.2f, %.2f, %.2f
                esc(first.getOrderAddress().getFirstName()),    // %s
                esc(first.getOrderAddress().getLastName()),     // %s
                esc(first.getOrderAddress().getAddress()),      // %s
                esc(first.getOrderAddress().getCity()),         // %s
                esc(first.getOrderAddress().getState()),        // %s
                esc(first.getOrderAddress().getZipCode()),      // %s
                esc(first.getOrderAddress().getPhone())         // %s
        );

        helper.setText(content, true);
        javaMailSender.send(message);
        return true;
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;")
                .replace("<","&lt;")
                .replace(">","&gt;")
                .replace("\"","&quot;");
    }



    // Send order status update email
    public boolean sendOrderStatusUpdateMail(ProductOrder order, String newStatus,
                                             HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {

        if (order == null || order.getOrderAddress() == null) return false;
        String to = order.getOrderAddress().getEmail();
        if (to == null || to.isBlank()) return false;

        MimeMessage message = javaMailSender.createMimeMessage();
        // enable UTF-8 and HTML
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("yaduvanshimrityunjay461@gmail.com", "Shopping Cart");
        helper.setTo(to);
        helper.setSubject("Order " + safe(order.getOrderId()) + " status updated: " + safe(newStatus));

        double qty   = order.getQuantity() == null ? 0 : order.getQuantity();
        double price = order.getPrice() == null ? 0.0 : order.getPrice();
        double total = qty * price;

        // Build a link to the order detail (adjust the path to your real route)
        String base = generateUrl(request);                 // e.g. http://localhost:8080
        String orderLink = base + "/user/order/" + safe(order.getOrderId());

        String body = """
      <div style="font-family:Arial,sans-serif;color:#333">
        <h2>Order Update</h2>
        <p>Hello %s %s,</p>
        <p>The status of your order <b>%s</b> has been updated to <b>%s</b>.</p>

        <table cellpadding="8" cellspacing="0" style="border-collapse:collapse;width:100%%">
          <thead>
            <tr style="background:#f5f5f5">
              <th align="left">Product</th>
              <th align="center">Qty</th>
              <th align="right">Price</th>
              <th align="right">Total</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>%s</td>
              <td align="center">%s</td>
              <td align="right">$%.2f</td>
              <td align="right">$%.2f</td>
            </tr>
          </tbody>
        </table>

        <p style="margin-top:16px">
          <a href="%s" style="display:inline-block;background:#0d6efd;color:#fff;padding:10px 14px;border-radius:6px;text-decoration:none">
            View Order
          </a>
        </p>

        <p>We’ll keep you posted on further updates.</p>
        <p>Thank you for shopping with us!</p>
      </div>
      """.formatted(
                esc(order.getOrderAddress().getFirstName()),
                esc(order.getOrderAddress().getLastName()),
                esc(order.getOrderId()),
                esc(newStatus),
                order.getProduct() != null ? esc(order.getProduct().getTitle()) : "-",
                (int) qty,
                price,
                total,
                orderLink
        );

        helper.setText(body, true);
        javaMailSender.send(message);
        return true;
    }

    // (Keep your existing esc(...) and generateUrl(...) methods)
    private static String safe(Object o) { return o == null ? "" : String.valueOf(o); }


    // Generate site URL for email links
    public static String generateUrl(HttpServletRequest request) {

        //8080/forgotPassword
        String siteUrl =  request.getRequestURL().toString();

        return siteUrl.replace(request.getServletPath(),"");

    }

}
