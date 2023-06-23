CREATE TABLE IF NOT EXISTS CARD_HOLDER(
        id UUID UNIQUE,
        client_id UUID NOT NULL UNIQUE,
        status VARCHAR(8) NOT NULL,
        credit_limit DOUBLE PRECISION NOT NULL,
        created_at TIMESTAMP NOT NULL,
        updated_at TIMESTAMP NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS BANK_ACCOUNT(
        id UUID UNIQUE,
        account CHAR(10),
        agency CHAR(4),
        bank_code CHAR(3),
        card_holder_id UUID NOT NULL UNIQUE,
        created_at TIMESTAMP NOT NULL,
        updated_at TIMESTAMP NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT fk_card_holder_iid FOREIGN KEY (card_holder_id) REFERENCES CARD_HOLDER (id)
);

CREATE TABLE IF NOT EXISTS CARD(
        id UUID UNIQUE,
        PRIMARY KEY (id),
        credit_limit DOUBLE PRECISION NOT NULL,
        card_number VARCHAR(19) NOT NULL,
        cvv INTEGER NOT NULL,
        due_date DATE NOT NULL,
        created_at TIMESTAMP NOT NULL,
        updated_at TIMESTAMP NOT NULL,
        card_holder_id UUID NOT NULL UNIQUE,
        CONSTRAINT fk_card_holder_id FOREIGN KEY (card_holder_id) REFERENCES CARD_HOLDER (id)
);
