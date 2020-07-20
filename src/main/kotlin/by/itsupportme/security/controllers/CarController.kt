package by.itsupportme.security.controllers

import by.itsupportme.security.model.Car
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cars")
class CarController {

    val carRepo = Array(4){
        Car(it.toLong(),"bmw $it")
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('car:read')")
    fun getOne(@PathVariable("id") id: Long){
        println(id)
    }

    @GetMapping
    @PreAuthorize("hasAuthority('car:read')")
    fun getAll(){
        println("all")
    }

    @PostMapping
    @PreAuthorize("hasAuthority('car:write')")
    fun createCar(@RequestBody car: Car){
        println(car)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('car:write')")
    fun update(@PathVariable("id") id: Long){
        println(id)
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('car:write')")
    fun delete(@PathVariable("id") id: Long){
        println(id)
    }
}