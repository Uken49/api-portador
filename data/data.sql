CREATE TABLE IF NOT EXISTS CARD_HOLDER(
        id UUID UNIQUE,
        clientId UUID UNIQUE,
        PRIMARY KEY (id),
        status BOOLEAN,
        credit_limit DOUBLE PRECISION,
        created_at TIMESTAMP,
        updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS CARD(
        id UUID UNIQUE,
        PRIMARY KEY (id),
        credit_limit DOUBLE PRECISION,
        card_number VARCHAR(19),
        cvv INTEGER,
        dueDate DATE,
        created_at TIMESTAMP,
        updated_at TIMESTAMP,
        card_holder_id UUID UNIQUE,
        CONSTRAINT fk_card_holder_id FOREIGN KEY (card_holder_id) REFERENCES CARD_HOLDER (id)
);
