package com.wlopezob.api_data_v1.controller;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wlopezob.api_data_v1.model.dto.PhoneRequest;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/webflux")
@Slf4j
public class WebFluxExampleController {

    @GetMapping("/simple-mono")
    public Mono<String> simpleMono() {
        return Mono.just("Hello WebFlux!");
    }

    @GetMapping("/simple-flux")
    public Flux<Integer> simpleFlux() {
        return Flux.range(1, 5);
    }

    @GetMapping("/delayed")
    public Mono<String> delayedResponse() {
        return Mono.just("Delayed response")
                .delayElement(Duration.ofSeconds(2));
    }

    @GetMapping("/transform")
    public Flux<String> transformExample() {
        return Flux.range(1, 5).delayElements(Duration.ofMillis(1000))
                .map(i -> "Number " + i)
                .doOnNext(s -> log.info("Processing: " + s))
                .filter(s -> s.contains("2") || s.contains("4"));
    }

    @GetMapping("/error-handling")
    public Mono<ResponseEntity<String>> errorHandling() {
        return Mono.just("Processing")
                .<String>flatMap(s -> Mono.error(new RuntimeException("Simulated error")))
                .onErrorReturn("Error occurred")
                .map(ResponseEntity::ok);
    }

    @GetMapping("/concat")
    public Flux<String> concatExample() {
        Flux<String> nombres = Flux.just("Juan", "Pedro", "María");
        Flux<String> apellidos = Flux.just("García", "López", "Martínez");

        // concat: emite elementos en secuencia (primero nombres, luego apellidos)
        return Flux.concat(nombres, apellidos)
                .doOnNext(item -> log.info("Concat emite: " + item));
        // Resultado: Juan, Pedro, María, García, López, Martínez
    }

    @GetMapping("/zip")
    public Flux<String> zipExample() {
        Flux<String> nombres = Flux.just("Juan", "Pedro", "María", "Ana");
        Flux<String> apellidos = Flux.just("García", "López", "Martínez");

        // zip: combina elementos en la misma posición de ambos Flux
        return Flux.zip(nombres, apellidos)
                .map(tuple -> tuple.getT1() + " " + tuple.getT2())
                .doOnNext(item -> log.info("Zip emite: " + item));
        // Resultado: Juan García, Pedro López, María Martínez
    }

    @GetMapping("/combined-flux")
    public Flux<String> combinedFlux() {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(1000))
                .doOnNext(item -> log.info("Flux1 emite: " + item));
        Flux<String> flux2 = Flux.just("X", "Y", "Z").delayElements(Duration.ofMillis(1000))
                .doOnNext(item -> log.info("flux2 emite: " + item));
        ;

        return Flux.concat(flux1, flux2);
    }

    @GetMapping("/zip-flux")
    public Flux<String> zipFlux() {
        Flux<String> flux1 = Flux.just("A", "B", "C", "D")
                .delayElements(Duration.ofMillis(1000))
                .doOnNext(item -> log.info("Flux1 emite: " + item));
        Flux<Integer> flux2 = Flux.just(1, 2, 3)
                .delayElements(Duration.ofMillis(1000))
                .doOnNext(item -> log.info("Flux2 emite: " + item));

        return Flux.zip(flux1, flux2, (s, i) -> s + i)
                .doOnNext(item -> log.info("Zip emite: " + item));
    }

    @GetMapping("/async-processing")
    public Flux<String> asyncProcessing() {
        return Flux.range(1, 5)
                .flatMap(i -> Mono.just("Processing " + i)
                        .delayElement(Duration.ofMillis(100)))
                .doOnNext(s -> log.info("Processing: " + s));
    }

    @GetMapping("/phone-processing")
    public Mono<List<PhoneRequest>> processPhones() {
        // Crear una lista de ejemplo de teléfonos
        List<PhoneRequest> phones = Arrays.asList(
                PhoneRequest.builder().number("123456").cityCode("1").contryCode("51").build(),
                PhoneRequest.builder().number("789012").cityCode("2").contryCode("52").build(),
                PhoneRequest.builder().number("345678").cityCode("1").contryCode("51").build(),
                PhoneRequest.builder().number("901234").cityCode("3").contryCode("53").build());

        // var rs = phones.stream().map(phone -> {
        // phone.setNumber("+" + phone.getContryCode() + phone.getCityCode() +
        // phone.getNumber());
        // return phone;
        // }).filter(phone ->
        // phone.getContryCode().equals("51")).collect(Collectors.toList());

        return Flux.fromIterable(phones)
                // Filter: solo teléfonos del código de país 51
                .filter(phone -> phone.getContryCode().equals("51"))
                // Map: modificar el formato del número
                .map(phone -> {
                    phone.setNumber("+" + phone.getContryCode() + phone.getCityCode() + phone.getNumber());
                    return phone;
                })
                // Collect: convertir el Flux a Mono<List>
                .collectList();
    }

    @GetMapping("/flatmap-examples")
    public Flux<String> flatMapExamples() {
        // Ejemplo 1: Flux to Mono to Flux
        return Flux.just("1", "2", "3")
                .flatMap(id -> {
                    // Simula obtener un usuario por ID
                    Mono<String> userMono = getUserById(id);
                    return userMono;
                });
    }

    @GetMapping("/nested-flatmap")
    public Mono<List<String>> nestedFlatMap() {
        return Mono.just("USER1")
                .flatMap(userId -> {
                    // Simula obtener teléfonos de un usuario
                    return getPhonesForUser(userId)
                            .flatMap(phone -> {
                                // Para cada teléfono, obtener más detalles
                                return getPhoneDetails(phone);
                            })
                            .collectList();
                });
    }

    @GetMapping("/flux-to-mono")
    public Mono<String> fluxToMono() {
        // var list = List.of("A", "B", "C").stream()
        //         .map(letter -> letter + "!").collect(Collectors.toList());
        // var cad = String.join("-", list);
        // return Mono.just(cad);

        return Flux.just("A", "B", "C")
        .flatMap(letter -> Mono.just(letter + "!"))
        .collectList()
        .map(list -> String.join("-", list));
    }

    // Métodos auxiliares para simular operaciones asíncronas
    private Mono<String> getUserById(String id) {
        return Mono.just("User: " + id)
                .delayElement(Duration.ofMillis(1000));
    }

    private Flux<String> getPhonesForUser(String userId) {
        return Flux.just(
                userId + "-Phone1",
                userId + "-Phone2",
                userId + "-Phone3").delayElements(Duration.ofMillis(1000));
    }

    private Mono<String> getPhoneDetails(String phone) {
        return Mono.just(phone + "-Details")
                .delayElement(Duration.ofMillis(50));
    }
}
