package ma.octo.aop.repository.impl;

import ma.octo.aop.entity.Language;
import ma.octo.aop.repository.LanguageRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class LanguageRepositoryImpl implements LanguageRepository {
  private JdbcTemplate jdbcTemplate;

  public LanguageRepositoryImpl(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }
  RowMapper<Language> rowMapper = (rs, rowNum) -> new Language(
          rs.getString("id"),
          rs.getString("name"),
          rs.getString("author"),
          rs.getString("fileExtension")
  );
  @Override
  public Optional<Language> findByExtension(final String extension) {
    String sql = "SELECT * FROM language WHERE fileExtension = ?";
    Language language = null;
    try {
      language = jdbcTemplate.queryForObject(sql, new Object[]{extension}, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
    return Optional.ofNullable(language);
  }

  @Override
  public int save(Language language) {
    String sql = "INSERT INTO language (id, name, author, fileExtension) VALUES (?, ?, ?, ?)";
    return jdbcTemplate.update(sql, language.getId(), language.getName(), language.getAuthor(), language.getFileExtension());
  }

  @Override
  public Optional<Language> findById(final String id) {
    String sql = "SELECT * FROM language WHERE id = ?";
    Language language = null;
    try {
      language = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
    return Optional.ofNullable(language);
  }

  @Override
  public List<Language> findAll() {
    String sql = "SELECT * FROM language";
    return jdbcTemplate.query(sql, rowMapper);
  }
}
