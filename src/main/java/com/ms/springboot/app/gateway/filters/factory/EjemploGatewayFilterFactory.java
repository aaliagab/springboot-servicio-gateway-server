package com.ms.springboot.app.gateway.filters.factory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

//Cada filtro factory debe tener un nombre de clase cualquiera con el sufijo GatewayFilterFactory
@Component
public class EjemploGatewayFilterFactory
		extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {

	private static Logger log = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

	public EjemploGatewayFilterFactory() {
		super(Configuracion.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public GatewayFilter apply(Configuracion config) {
		// TODO Auto-generated method stub
		return (exchange, chain) -> {

			log.info("EJECUTANDO PRE GATEWAY FILTER FACTORY: " + config.mensaje);

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("EJECUTANDO POST GATEWAY FILTER FACTORY: " + config.mensaje);
				// AGREGANDO COOKIES A LAS CABECERAS EN EL RESPONSE
				Optional.ofNullable(config.cookieValor).ifPresent(valor -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, valor).build());
				});

			}));
		};
	}

	public static class Configuracion {
		private String mensaje;
		private String cookieValor;
		private String cookieNombre;

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

		public String getCookieValor() {
			return cookieValor;
		}

		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}

		public String getCookieNombre() {
			return cookieNombre;
		}

		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}

	}

}
