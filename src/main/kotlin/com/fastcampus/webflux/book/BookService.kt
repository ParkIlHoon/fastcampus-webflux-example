package com.fastcampus.webflux.book

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.atomic.AtomicLong

@Service
class BookService {

    private final val nextId = AtomicLong(0)

    val books = mutableListOf(
        Book(id = nextId.incrementAndGet(), name = "책1", price = 5000),
        Book(id = nextId.incrementAndGet(), name = "책2", price = 7000)
    )
//    fun getAll(): Flux<Book> = Flux.fromIterable(books)
    fun getAll(): Flux<Book> = books.toFlux()
//    fun get(id: Long): Mono<Book> =
//        Mono.justOrEmpty(
//            books.find { id == it.id }
//        )
    fun get(id: Long): Mono<Book> =
        books.find { id == it.id }.toMono()


    fun add(request: Map<String, Any>): Mono<Book> =
        Mono.just(request)
            .map { map ->
                val book = Book(
                    id = nextId.incrementAndGet(),
                    name = map["name"].toString(),
                    price = map["price"] as Int
                )
                books.add(book)
                book
            }

    fun delete(id: Long): Mono<Void> =
        Mono.justOrEmpty(books.find { id == it.id })
            .map { books.remove(it) }
            .then()

}

data class Book(
    val id: Long,
    val name: String,
    val price: Int,
)