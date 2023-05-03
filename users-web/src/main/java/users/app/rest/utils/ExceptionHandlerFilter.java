package users.app.rest.utils;


import org.springframework.web.filter.OncePerRequestFilter;
import users.exceptions.AppException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (AppException e) {
			response.getWriter().write( ResponseMessage.packageAndJsoniseError(e.getError()));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		catch (JwtException e) {
//			response.getWriter().write( ResponseMessage.packageAndJsoniseError(UsersError.SESSION_EXPIRED));
//		}
	}

}