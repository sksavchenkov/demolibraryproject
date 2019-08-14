package ru.bis.demolibraryproject.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bis.demolibraryproject.model.Author;
import ru.bis.demolibraryproject.specification.AuthorSpecification;
import ru.bis.demolibraryproject.specification.SearchCriteria;

import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.core.Is.*;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { PersistenceJPAConfig.class })
@Transactional
@TransactionConfiguration
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    private Author authorMarks;
    private Author authorTom;

    @Before
    public void init() {
        authorMarks = new Author();
        authorMarks.setFullName("Karl Marx");
        authorRepository.save(authorMarks);

        authorTom = new Author();
        authorTom.setFullName("Thomas Kyte");
        authorRepository.save(authorTom);
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        AuthorSpecification spec =
                new AuthorSpecification(new SearchCriteria("FullName", ":",  "Karl"));

        List<Author> results = authorRepository.findAll(spec);

        assertThat(results.get(0), is(authorMarks));
    }
}