package com.eCommerce.serviceImpl;

import com.eCommerce.service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public void removeSessionMessage() {
        HttpServletRequest request = ((ServletRequestAttributes)
                (RequestContextHolder.getRequestAttributes()))
                .getRequest();

        request.getSession().removeAttribute("errorMsg");
        request.getSession().removeAttribute("successMsg");
    }
}
