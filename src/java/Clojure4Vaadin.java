package com.example.testvaadin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import clojure.lang.RT;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

public class Clojure4Vaadin extends AbstractApplicationServlet {

	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		return Application.class;
	}

	@Override
	protected Application getNewApplication(HttpServletRequest request) throws ServletException {
		try {

			//load script, with name provided as a servlet's parameter
			RT.load(getServletConfig().getInitParameter("script-name"), true);

			//run Lisp function
			return (Application)RT.var(getServletConfig().getInitParameter("package-name"),
	                				getServletConfig().getInitParameter("function-name"))
						.invoke(new String[0]);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

}