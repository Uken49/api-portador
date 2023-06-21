CREATE TABLE IF NOT EXISTS CARD_HOLDER(
        id UUID UNIQUE,
        client_id UUID UNIQUE,
        status VARCHAR(8),
        credit_limit DOUBLE PRECISION,
        created_at TIMESTAMP,
        updated_at TIMESTAMP,
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS BANK_ACCOUNT(
        id UUID UNIQUE,
        account CHAR(10),
        agency CHAR(4),
        bank_code CHAR(3),
        card_holder_id UUID UNIQUE,
        created_at TIMESTAMP,
        updated_at TIMESTAMP,
        PRIMARY KEY (id),
        CONSTRAINT fk_card_holder_iid FOREIGN KEY (card_holder_id) REFERENCES CARD_HOLDER (id)
);

CREATE TABLE IF NOT EXISTS CARD(
        id UUID UNIQUE,
        PRIMARY KEY (id),
        credit_limit DOUBLE PRECISION,
        card_number VARCHAR(19),
        cvv INTEGER,
        due_date DATE,
        created_at TIMESTAMP,
        updated_at TIMESTAMP,
        card_holder_id UUID UNIQUE,
        CONSTRAINT fk_card_holder_id FOREIGN KEY (card_holder_id) REFERENCES CARD_HOLDER (id)
);
