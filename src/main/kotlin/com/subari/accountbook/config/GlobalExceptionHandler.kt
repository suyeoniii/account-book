package com.subari.accountbook.config

import com.subari.accountbook.util.BaseException
import com.subari.accountbook.util.BaseResponse
import com.subari.accountbook.util.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.lang.model.type.NullType


@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(BaseException::class)
    protected fun handleBaseException(e: BaseException): ResponseEntity<Any> {
        println("base")
        return ResponseEntity.status(e.baseResponseCode.status)
            .body(BaseResponse<NullType>(e.baseResponseCode))
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val error = ex.bindingResult.fieldErrors[0]
        if(error.defaultMessage!!.split(':').size > 1){
            val code = error.defaultMessage!!.split(':')[0].toInt()
            val message = error.defaultMessage!!.split(':')[1]
            val errorResponse = ErrorResponse(false, code, message)
            return ResponseEntity(errorResponse, headers, status)
        }
        println("400 error")
        return ResponseEntity(ErrorResponse(false, 4000, error.defaultMessage!!), headers, status)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        println("exception")
        var errorResponseDto: ErrorResponse
        errorResponseDto = if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            ErrorResponse(false, 5000, "Internal Server Error")
        } else if(status == HttpStatus.BAD_REQUEST) {
            ErrorResponse(false, 4000, "Bad Request")
        } else {
            ErrorResponse(false, status.value(), ex.message!!)
        }
        return ResponseEntity(errorResponseDto, headers, status)
    }
}