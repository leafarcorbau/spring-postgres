package com.dh.sp.postgres.model;

import com.dh.sp.postgres.model.identity.BookId;
import com.dh.sp.postgres.util.TestUtil;

import com.dh.sp.postgres.model.Book;
import com.dh.sp.postgres.model.BookGenre;
import java.util.UUID;

public class TestBook{

    public static Book.BookBuilder getInstance(final UUID seed){
        return Book.builder()
                .id(new BookId(seed))
                .title(TestUtil.genField("title", seed))
                .copiesCount(1)
                .author(TestUtil.genField("author", seed))
                .bookGenre(BookGenre.HORROR);
    }
}
