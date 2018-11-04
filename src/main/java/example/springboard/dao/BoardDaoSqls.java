package example.springboard.dao;

class BoardDaoSqls {
    static final String GET_BOARD_COUNT = "SELECT count(*) FROM board WHERE category_id=:category_id AND is_deleted=0";
    static final String GET_BOARD_LIST_ALL =
            "SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.name, b.title, b.ip_addr, b.reg_date " +
            "FROM board AS b INNER JOIN member AS m ON b.member_id = m.id " +
            "WHERE category_id = :category_id and is_deleted=0 " +
            "ORDER BY origin_id DESC, reply_seq ASC LIMIT :pageStart , :perPageNum";

    static String GET_BOARD_LIST_BY_SERACH =
            "SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.name, b.title, b.ip_addr, b.reg_date " +
            "FROM board AS b INNER JOIN member AS m ON b.member_id = m.id " +
            "WHERE category_id = :category_id AND ";

    static final String GET_BOARD_LIST_BY_MEMBER =
            "SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.name, b.title, b.ip_addr, b.reg_date " +
            "FROM board AS b INNER JOIN member AS m ON b.member_id = m.id " +
            "WHERE category_id = :category_id AND m.name = m.name " +
            "ORDER BY b.origin_id DESC, b.reply_seq ASC";
    static final String GET_BOARD_LIST_BY_TITLE =
            "SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.name, b.title, b.ip_addr, b.reg_date " +
            "FROM board AS b INNER JOIN member AS m ON b.member_id = m.id " +
            "WHERE b.category_id = :b.category_id AND b.title LIKE :b.title " +
            "ORDER BY b.origin_id DESC, b.reply_seq ASC";
    static final String GET_BOARD_LIST_BY_CONTENT =
            "SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.name, b.title, b.ip_addr, b.reg_date " +
            "FROM board AS b INNER JOIN member AS m ON b.member_id = m.id " +
            "INNER JOIN board_body AS bb ON b.id = bb.id " +
            "WHERE b.category = :b.category AND bb.content LIKE :bb.content AND b.is_deleted = 0 " +
            "ORDER BY b.origin_id DESC, b.reply_seq ASC";
    static final String GET_BOARD_LIST_BY_TITLE_OR_CONTENT =
            "SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.name, b.title, b.ip_addr, b.reg_date " +
            "FROM board AS b INNER JOIN member AS m ON b.member_id = m.id " +
            "INNER JOIN board_body AS bb ON b.id = bb.id " +
            "WHERE b.category=:b.category AND " +
            "(bb.content LIKE :bb.content OR b.title LIKE :b.title) AND b.is_deleted = 0" +
            "ORDER BY b.origin_id DESC, b.reply_seq ASC";
    static final String GET_BOARD_DETAIL =
            "SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.name, b.title, b.ip_addr, b.reg_date, bb.content " +
            "FROM board AS b INNER JOIN member AS m ON b.member_id = m.id " +
            "INNER JOIN board_body AS bb ON b.id = bb.id " +
            "WHERE bb.id = :b.id";
    static final String ADD_BOARD = "INSERT INTO board (id, origin_id, depth, reply_seq, category_id, member_id, title, ip_addr, reg_date) " +
            "VALUES (null, :origin_id, :depth, :reply_seq, :category_id, :member_id, :title, :ip_addr, NOW())";
    static final String ADD_BOARD_BODY = "INSERT INTO board_body (id, content) VALUES (:board_id, :content)";
    static final String UPDATE_BOARD = "UPDATE board SET title = :title, reg_date = NOW(), ip_addr = :ip_addr " +
            "WHERE member_id = :member_id and id = :id";
    static final String UPDATE_BOARD_BODY = "UPDATE board_body SET content = :content WHERE id = :id";

    static final String UPDATE_BOARD_FOR_REPLY = "UPDATE board SET reply_seq = reply_seq + 1 " +
            "WHERE origin_id = :origin_id AND reply_seq > :reply_seq";
    static final String GET_BOARD_INFO_FOR_REPLY = "SELECT origin_id, depth, reply_seq " +
            "FROM board WHERE id = :id";

    static final String DELETE_BOARD = "UPDATE board SET is_deleted = 1 WHERE id = :id";
}
