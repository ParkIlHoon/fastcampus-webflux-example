package com.fastcampus.webflux.book

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService
) {
    @GetMapping
    fun getAll(): Flux<Book> = bookService.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Mono<Book> = bookService.get(id)

    @PostMapping
    fun add(@RequestBody request: Map<String, Any>): Mono<Book> = bookService.add(request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Mono<Void> = bookService.delete(id)
}