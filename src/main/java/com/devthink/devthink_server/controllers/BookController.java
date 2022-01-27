package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.dto.BookDetailResponseDto;
import com.devthink.devthink_server.dto.BookResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReviewService reviewService;

    /**
     * 책 리스트를 전달된 pageable 기준에 따라 가져옵니다.
     * [GET] /books/list?page= &size= &sort= ,정렬방식
     * @return Pageable 기준에 따라 정렬된 책 리스트
     */
    @GetMapping("/list")
    @ApiOperation(value = "책 리스트 조회", notes = "책 전체 리스트를 전달된 pageable 파라미터에 따라 정렬하여 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Page<BookResponseDto> list(Pageable pageable) {
        return bookService.getBooks(pageable);
    }

    /**
     * 리뷰가 가장 많이 달린 책을 가져옵니다.
     * [GET] /books/most
     * @return BookResponseDto 리뷰가 가장 많이 달린 책
     */
    @GetMapping("/most")
    @ApiOperation(value = "리뷰가 가장 많이 달린 책 조회", notes = "리뷰가 가장 많이 달린 책을 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BookResponseDto mostOne() {
        return bookService.getMostReviewCntBook();
    }

    /**
     * 입력한 식별자 값(id)에 해당하는 책의 상세 정보를 가져옵니다.
     * [GET] /books/{id}
     * @return Book
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "책 상세 조회", notes = "식별자 값의 책을 상세 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BookDetailResponseDto detail(@PathVariable("id") @ApiParam(value="책 식별자 값") Long id) {
        return BookDetailResponseDto.builder()
                .book(bookService.getBookById(id).toBookResponseDto())
                .reviews(reviewService.getReviewsByBookId(id))
                .build();
    }


}
