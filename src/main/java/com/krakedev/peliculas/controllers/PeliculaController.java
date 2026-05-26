package com.krakedev.peliculas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.peliculas.entidades.Pelicula;
import com.krakedev.peliculas.services.ServicioPelicula;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
	private final ServicioPelicula service;

	public PeliculaController(ServicioPelicula service) {
		super();
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Pelicula pelicula){
		try {
			Pelicula nuevo = service.crear(pelicula);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear nueva pelicula: "+pelicula);
		}
	};
	
	@GetMapping
	public ResponseEntity<?> listar(){
		try {
			List<Pelicula> pelis = service.listar();
			return ResponseEntity.ok(pelis);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar las peliculas");
		}
	};
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id){
		try {
			Pelicula existe = service.buscarPorId(id);
			if(existe == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La pelicula con el id: "+id+" no fue encontrada");
			}
			return ResponseEntity.ok(existe);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la pelicula");
		}
	};
	
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pelicula pelicula){
		try {
			Pelicula actual = service.actualizar(id, pelicula);
			if(actual == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La pelicula con el id: "+id+" no existe");
			}
			return ResponseEntity.ok(actual);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar");
		}
	};
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		try {
			boolean eliminado = service.eliminar(id);
			if(!eliminado) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La pelicula con el id: "+id+" no fue encontrada");
			}
			return ResponseEntity.ok("Pelicula eliminada");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar");
		}
	};
	
	@GetMapping("/genero")
	public ResponseEntity<?> buscarPorGenero(@RequestParam String genero){
		try {
			List<Pelicula> pelis=service.buscarPorGenero(genero);
			return ResponseEntity.ok(pelis);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar por genero");
		}
	};
	
	@GetMapping("/disponible")
	public ResponseEntity<?> buscarPorDisponible(@RequestParam boolean disponible){
		try {
			List<Pelicula> pelis=service.buscarPorDisponible(disponible);
			return ResponseEntity.ok(pelis);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar por disponibilidad");
		}
	};

}
