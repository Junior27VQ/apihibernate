package com.krakedev.peliculas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.peliculas.entidades.Pelicula;
import com.krakedev.peliculas.repository.PeliculaRepository;

@Service
public class ServicioPelicula {
	private final PeliculaRepository repository;

	public ServicioPelicula(PeliculaRepository repository) {
		super();
		this.repository = repository;
	}
	
	public Pelicula crear(Pelicula peli) {
		return repository.save(peli);
	};
	public List<Pelicula> listar(){
		return repository.findAll();
	};
	public Pelicula buscarPorId(Long id) {
		Optional<Pelicula> peli = repository.findById(id);
		return peli.orElse(null);
	};
	public Pelicula actualizar(Long id, Pelicula peli) {
		Pelicula encontrado = buscarPorId(id);
		if(encontrado == null) {
			return null;
		}
		encontrado.setNombre(peli.getNombre());
		encontrado.setDirector(peli.getDirector());
		encontrado.setGenero(peli.getGenero());
		encontrado.setDuracion(peli.getDuracion());
		encontrado.setDisponible(peli.isDisponible());
		encontrado.setCalificacion(peli.getCalificacion());
		
		return repository.save(encontrado);
	};
	public boolean eliminar(Long id) {
		Pelicula peli = buscarPorId(id);
		if(peli == null) {
			return false;
		}
		repository.deleteById(id);
		return true;
	};
	
	public List<Pelicula> buscarPorGenero(String genero){
		return repository.findByGenero(genero);
	}
	public List<Pelicula> buscarPorDisponible(boolean disponible){
		return repository.findByDisponible(disponible);
	}

}
