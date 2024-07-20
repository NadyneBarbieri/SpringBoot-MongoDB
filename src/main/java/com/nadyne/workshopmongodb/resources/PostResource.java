package com.nadyne.workshopmongodb.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nadyne.workshopmongodb.domain.Post;
import com.nadyne.workshopmongodb.repository.PostRepository;
import com.nadyne.workshopmongodb.resources.util.URL;
import com.nadyne.workshopmongodb.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired
	private PostService service;
	@Autowired
	private PostRepository repository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/titlesearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findByIdTitle(@RequestParam(value = "text", defaultValue = "") String text) {
		text = URL.decodeParams(text);
		List<Post> list = service.findByTitle(text);
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/fullsearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> fullsearch(@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate) {
		text = URL.decodeParams(text);
		Date min = URL.convertate(minDate, new Date(0L));
		Date max = URL.convertate(maxDate, new Date());
		List<Post> list = service.fullSearch(text, min, max);
		return ResponseEntity.ok().body(list);
	}

	@PostMapping
	public ResponseEntity<Post> postPostagem(@RequestBody Post postagem){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(postagem));
	}
	
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable String id) {
		repository.deleteById(id);
	}
	
	@PutMapping
	public ResponseEntity<Post> putPostagem(@RequestBody Post postagem){
		return ResponseEntity.ok(repository.save(postagem));
	}

}
