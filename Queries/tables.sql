CREATE TABLE dbo.category(
cat_id INT PRIMARY KEY IDENTITY(1,1),
cat_name VARCHAR(MAX)
);

CREATE TABLE dbo.Users (
    uid INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(MAX),
    email NVARCHAR(100) UNIQUE NOT NULL,
    pwd VARBINARY(MAX) NOT NULL,
    role INT,
    active_yn VARCHAR(5),
    phone VARCHAR(10) UNIQUE NOT NULL,
    DateTime DATETIME DEFAULT GETDATE()
);

CREATE TABLE dbo.Catalog (
    cid INT PRIMARY KEY IDENTITY(1,1),
    cat_id INT,
    image NVARCHAR(MAX),
    name VARCHAR(30),
    SKU VARCHAR(30),
    Color VARCHAR(20),
    Qty INT,
    Price INT,
    Description VARCHAR(50),
    FOREIGN KEY (cat_id) REFERENCES dbo.Category(cat_id)
);


CREATE TABLE dbo.Request (
    rid INT PRIMARY KEY IDENTITY(1,1),
    uid INT,
    cid INT,
    RStatus VARCHAR(15),
    RQty INT,
    RDesc VARCHAR(200),
    FOREIGN KEY (uid) REFERENCES dbo.Users(uid),
    FOREIGN KEY (cid) REFERENCES dbo.Catalog(cid)
);

CREATE TABLE dbo.photos(
	PID INT PRIMARY KEY IDENTITY(1,1),
	image nvarchar(MAX),
	color nvarchar(MAX),
	description nvarchar(MAX),
	name nvarchar(MAX),
	price INT,
	qty INT,
	status VARCHAR(MAX),
	uid int,
	cat_id int,
	DATETIME dateTime DEFAULT GETDATE(),
	FOREIGN KEY (uid) REFERENCES dbo.users(uid),
	FOREIGN KEY (cat_id) REFERENCES dbo.category(cat_id)
)
