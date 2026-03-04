# Haoke API Documentation

> Auto-generated API documentation based on the project's controller layer.

## Table of Contents

- [Common Response Structures](#common-response-structures)
- [1. User Module](#1-user-module)
- [2. House Resources Module](#2-house-resources-module)
- [3. Advertisement Module](#3-advertisement-module)
- [4. Contract Module](#4-contract-module)
- [5. Landlord (FangDong) Module](#5-landlord-fangdong-module)
- [6. Favorite Module](#6-favorite-module)
- [7. See House (Viewing Request) Module](#7-see-house-viewing-request-module)
- [8. Rent Out Order Module](#8-rent-out-order-module)
- [9. Notice Message Module](#9-notice-message-module)
- [10. Identity Request Module](#10-identity-request-module)
- [11. Search Module](#11-search-module)
- [12. Picture Upload Module](#12-picture-upload-module)
- [13. Menu Module](#13-menu-module)
- [14. GraphQL Module](#14-graphql-module)
- [15. IM (Instant Messaging) Module](#15-im-instant-messaging-module)

---

## Common Response Structures

### RestResponse\<T\>

Standard REST response wrapper used across most endpoints.

| Field | Type | Description |
|-------|------|-------------|
| `resultCode` | `int` | Response code. `200` = success, `500` = failure |
| `resultMsg` | `String` | Response message |
| `data` | `T` | Response payload |

### TableResult\<T\>

Paginated table result, typically wrapped inside `RestResponse`.

| Field | Type | Description |
|-------|------|-------------|
| `list` | `List<T>` | List of records |
| `pagination` | `Pagination` | Pagination metadata |

### Pagination

| Field | Type | Description |
|-------|------|-------------|
| `current` | `Integer` | Current page number |
| `pageSize` | `Integer` | Page size |
| `total` | `Long` | Total record count |

### WebResult

Response wrapper used by the advertisement module.

| Field | Type | Description |
|-------|------|-------------|
| `data` | `Map<String, Object>` | Contains `list` key with result data |
| `meta` | `Map<String, Object>` | Contains `status` (int) and `msg` (String) |

### SearchResult

| Field | Type | Description |
|-------|------|-------------|
| `totalPage` | `Integer` | Total page count |
| `list` | `List<HouseData>` | List of house data |
| `hotWord` | `Set<String>` | Hot search keywords |

---

## 1. User Module

Base path: `/user`

### 1.1 Login (Management Backend)

- **URL**: `POST /user/login`
- **Description**: User login for management backend
- **Request Body** (`LoginReqDto`):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `loginCode` | `String` | Yes | Login identifier (phone number or username) |
| `password` | `String` | Yes | Password |
| `loginFlag` | `String` | Yes | Login source flag (`1`: management backend, `2`: app) |

- **Response**: `Map<String, Object>`

| Field | Type | Description |
|-------|------|-------------|
| `status` | `String` | `"ok"` on success, `"error"` on failure |
| `currentAuthority` | `String` | `"admin"` or `"houseOwner"` |
| `data` | `TokenDto` | Token data (on successful non-admin login) |
| `errorMsg` | `String` | Error message (on failure) |

---

### 1.2 Login (App)

- **URL**: `POST /user/app/login`
- **Description**: User login for mobile app
- **Request Body** (`AppLoginDto`):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `username` | `String` | Yes | Username |
| `password` | `String` | Yes | Password |

- **Response**: `RestResponse`

---

### 1.3 User List

- **URL**: `POST /user/list`
- **Description**: Query user list with pagination
- **Request Body** (`UserSearchDto`): Search criteria
- **Response**: `RestResponse<TableResult>`

---

### 1.4 User Detail

- **URL**: `GET /user/{id}`
- **Description**: Get tenant (user) details by ID
- **Path Parameters**:

| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | `Long` | User ID |

- **Response**: `RestResponse<UserEo>`

---

### 1.5 Current User

- **URL**: `GET /user/app/current`
- **Description**: Get currently logged-in user info (requires authentication)
- **Response**: `RestResponse<UserEo>`

---

### 1.6 Register

- **URL**: `POST /user/register`
- **Description**: Register a new user
- **Request Body** (`AppLoginDto`):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `username` | `String` | Yes | Username |
| `password` | `String` | Yes | Password |

- **Response**: `RestResponse`

---

### 1.7 Update User

- **URL**: `PUT /user/update`
- **Description**: Update user information
- **Request Body** (`UserEo`): User entity with `id` (required) and fields to update
- **Response**: `RestResponse<Void>`

---

## 2. House Resources Module

Base path: `/house/resources`

### 2.1 Create House Resource

- **URL**: `POST /house/resources`
- **Description**: Create a new house listing (requires authentication)
- **Request Body** (`HouseResources`):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `title` | `String` | Yes | House listing title |
| `rent` | `Integer` | Yes | Monthly rent (CNY) |
| `rentMethod` | `Integer` | No | Rental method: `1` = whole unit, `2` = shared |
| `houseType` | `String` | No | House type (e.g., "2室1厅1卫") |
| `houseAddress` | `String` | No | Address |
| `floor` | `String` | No | Floor (e.g., "8/26") |
| `orientation` | `Integer` | No | Orientation: east/south/west/north |
| `image` | `String` | No | Image filenames (comma-separated, max 5) |
| `houseDesc` | `String` | No | Description |
| `contact` | `String` | No | Contact person name |
| `mobile` | `String` | No | Contact phone number |
| `time` | `Integer` | No | Viewing time: `1`=morning, `2`=noon, `3`=afternoon, `4`=evening, `5`=all day |
| `decoration` | `Integer` | No | Decoration: `1`=premium, `2`=simple, `3`=unfurnished |
| `facilities` | `String` | No | Supporting facilities |

- **Response**: `201 Created` on success, `500 Internal Server Error` on failure

---

### 2.2 Query House Resource List

- **URL**: `GET /house/resources`
- **Description**: Query house resource list with pagination (requires authentication)
- **Query Parameters**:

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `currentPage` | `Integer` | No | `1` | Current page number |
| `pageSize` | `Integer` | No | `10` | Page size |
| *(HouseResourceReqDto fields)* | - | No | - | Additional filter criteria |

- **Response**: `RestResponse<TableResult>`

---

### 2.3 Update House Resource

- **URL**: `PUT /house/resources`
- **Description**: Update an existing house listing
- **Request Body** (`HouseResources`): House resource entity with fields to update
- **Response**: `RestResponse<Void>`

---

### 2.4 Query House Resource by ID

- **URL**: `GET /house/resources/{id}`
- **Description**: Get house resource details by ID
- **Path Parameters**:

| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | `String` | House resource ID |

- **Response**: `RestResponse<HouseResourcesVo>`

---

### 2.5 Query All House Resources

- **URL**: `GET /house/resources/queryAll`
- **Description**: Query all house resources from Elasticsearch (limit 3000)
- **Response**: `RestResponse<List<HouseData>>`

---

### 2.6 Query by Rent Status

- **URL**: `GET /house/resources/query/rentStatus`
- **Description**: Query house statistics grouped by rent status (requires authentication)
- **Response**: `RestResponse<Map>`

---

### 2.7 Delete House Resource

- **URL**: `DELETE /house/resources/{id}`
- **Description**: Delete a house resource by ID
- **Path Parameters**:

| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | `String` | House resource ID |

- **Response**: `RestResponse<Void>`

---

## 3. Advertisement Module

Base path: `/ad`

### 3.1 Query Index Advertisements

- **URL**: `GET /ad`
- **Description**: Query homepage advertisements (supports CORS)
- **Response**: `WebResult`

Response `data.list` item structure:

| Field | Type | Description |
|-------|------|-------------|
| `original` | `String` | Advertisement image URL |

---

## 4. Contract Module

Base path: `/contract`

### 4.1 Contract List (Paginated)

- **URL**: `POST /contract/listByPage`
- **Description**: Query contract list by page
- **Request Body** (`ContractReqDto`): Search criteria with pagination
- **Response**: `RestResponse<TableResult>`

---

### 4.2 Create Contract

- **URL**: `POST /contract/add`
- **Description**: Create a new rental contract (requires authentication)
- **Request Body** (`ContractDto`):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `contractCode` | `String` | No | Contract code |
| `contractStatus` | `Integer` | No | Status: `0`=pending, `1`=active, `2`=ended, `3`=overdue |
| `contractContent` | `String` | No | Contract details |
| `houseName` | `String` | No | House name |
| `houseId` | `String` | No | House ID |
| `ownerId` | `Long` | No | Landlord ID |
| `ownerName` | `String` | No | Landlord name |
| `endTime` | `Date` | No | Contract end time |

> Note: `customerId`, `customerMobile`, and `customerName` are auto-filled from the logged-in user session.

- **Response**: `RestResponse<Long>` (returns new contract ID)

---

### 4.3 Query Contract by ID

- **URL**: `GET /contract/{id}`
- **Description**: Get contract details by ID
- **Path Parameters**:

| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | `Long` | Contract ID |

- **Response**: `RestResponse<ContractEo>`

---

### 4.4 Update Contract Status

- **URL**: `PUT /contract/updateStatus`
- **Description**: Update contract status
- **Query Parameters**:

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | `Long` | Yes | Contract ID |
| `contractStatus` | `Integer` | Yes | New status: `0`=pending, `1`=active, `2`=ended, `3`=overdue |

- **Response**: `RestResponse<Void>`

---

## 5. Landlord (FangDong) Module

Base path: `/fangDong`

### 5.1 Landlord List

- **URL**: `GET /fangDong/list`
- **Description**: Query landlord list with pagination
- **Query Parameters**:

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `pageNum` | `Integer` | No | `1` | Page number |
| `pageSize` | `Integer` | No | `10` | Page size |
| *(UserSearchDto fields)* | - | No | - | Additional search criteria |

- **Response**: `RestResponse<TableResult>`

---

### 5.2 Landlord Detail

- **URL**: `GET /fangDong/{id}`
- **Description**: Get landlord details by ID
- **Path Parameters**:

| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | `Long` | Landlord user ID |

- **Response**: `RestResponse<UserEo>`

---

## 6. Favorite Module

Base path: `/favorite`

### 6.1 Query Favorites (Paginated)

- **URL**: `POST /favorite/queryByPage`
- **Description**: Query user favorites by page
- **Request Body** (`FavoriteDto`): Search criteria with pagination
- **Response**: `RestResponse<PageInfo>`

---

### 6.2 Add Favorite

- **URL**: `POST /favorite`
- **Description**: Add a house to favorites (requires authentication)
- **Request Body** (`FavoriteEo`):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `houseId` | `String` | Yes | House resource ID |
| `houseTitle` | `String` | No | House title |

> Note: `userId` is auto-filled from the logged-in user session.

- **Response**: `RestResponse<Long>` (returns new favorite ID)

---

### 6.3 Delete Favorite

- **URL**: `DELETE /favorite`
- **Description**: Remove a house from favorites (requires authentication)
- **Query Parameters** (`FavoriteEo` fields):

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `houseId` | `String` | Yes | House resource ID |

- **Response**: `RestResponse<Void>`

---

## 7. See House (Viewing Request) Module

Base path: `/see/house`

### 7.1 Query Viewing Request by ID

- **URL**: `GET /see/house/{id}`
- **Description**: Get viewing request details
- **Path Parameters**:

| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | `Long` | Viewing request ID |

- **Response**: `RestResponse<SeeHouseRecordVo>`

---

### 7.2 Save/Update Viewing Request

- **URL**: `POST /see/house/update`
- **Description**: Save or update a viewing request
- **Request Body** (`SeeHouseRequestEo`):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `tenantName` | `String` | No | Tenant name |
| `tenantId` | `Long` | No | Tenant ID |
| `tenantMobile` | `String` | No | Tenant phone |
| `seeHouseTime` | `Date` | No | Viewing time (format: `yyyy-MM-dd HH:mm`) |
| `seeHouseStatus` | `Integer` | No | Status: `0`=pending, `1`=confirmed, `2`=cancelled, `3`=completed |
| `houseId` | `String` | No | House resource ID |
| `houseName` | `String` | No | House name |
| `houseOwnerId` | `Long` | No | Landlord ID |
| `houseOwnerName` | `String` | No | Landlord name |

- **Response**: `RestResponse<Void>`

---

### 7.3 Update Viewing Request Status

- **URL**: `PUT /see/house/updateRequestStatus`
- **Description**: Update viewing request status
- **Query Parameters**:

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | `Long` | Yes | Request ID |
| `seeHouseStatus` | `Integer` | Yes | New status: `0`=pending, `1`=confirmed, `2`=cancelled, `3`=completed |

- **Response**: `RestResponse<Void>`

---

### 7.4 Add Viewing Request

- **URL**: `POST /see/house/add`
- **Description**: Create a new viewing request
- **Request Body** (`SeeHouseRequestEo`): Same as section 7.2
- **Response**: `RestResponse<Long>` (returns new request ID)

---

### 7.5 Viewing Request List

- **URL**: `GET /see/house/list`
- **Description**: Query viewing requests with pagination
- **Query Parameters**:

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `pageNum` | `Integer` | No | `1` | Page number |
| `pageSize` | `Integer` | No | `10` | Page size |
| *(SeeHouseReqDto fields)* | - | No | - | Additional filter criteria |

- **Response**: `RestResponse<PageInfo>`

---

## 8. Rent Out Order Module

Base path: `/rent/out/order`

### 8.1 Create Rent Order

- **URL**: `POST /rent/out/order`
- **Description**: Create a rental order (requires authentication)
- **Request Body** (`RentOutOrderEo`):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `houseId` | `String` | Yes | House resource ID |
| `houseOwnerId` | `Long` | Yes | Landlord ID |
| `money` | `Integer` | No | Transaction amount |
| `remark` | `String` | No | Remark |
| `rentStartTime` | `Date` | No | Rental start time |
| `rentEndTime` | `Date` | No | Rental end time |

> Note: `tenantId` is auto-filled from the logged-in user session.

- **Response**: `RestResponse<Long>` (returns new order ID)

---

### 8.2 Update Order Status

- **URL**: `PUT /rent/out/order/updateStatus`
- **Description**: Update rental order status
- **Query Parameters**:

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | `Long` | Yes | Order ID |
| `status` | `Integer` | Yes | New status: `0`=created/pending landlord, `1`=pending payment, `2`=paid |

- **Response**: `RestResponse<Void>`

---

### 8.3 Order Detail

- **URL**: `GET /rent/out/order/detail/{id}`
- **Description**: Get rental order details
- **Path Parameters**:

| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | `Long` | Order ID |

- **Response**: `RestResponse<Void>` (not yet implemented)

---

### 8.4 Order List

- **URL**: `GET /rent/out/order/list`
- **Description**: Query rental orders with pagination
- **Query Parameters**:

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `houseId` | `String` | No | - | Filter by house ID |
| `tenantId` | `Long` | No | - | Filter by tenant ID |
| `houseOwnerId` | `Long` | No | - | Filter by landlord ID |
| `status` | `Integer` | No | - | Filter by order status |
| `pageNum` | `Integer` | No | `1` | Page number |
| `pageSize` | `Integer` | No | `10` | Page size |

- **Response**: `RestResponse<PageInfo<RentOutOrderVo>>`

---

## 9. Notice Message Module

Base path: `/notice/message`

### 9.1 Query Notice Messages

- **URL**: `GET /notice/message`
- **Description**: Query notice message list for current user
- **Response**: `RestResponse<List<NoticeMessageEo>>`

**NoticeMessageEo fields**:

| Field | Type | Description |
|-------|------|-------------|
| `id` | `Long` | Message ID |
| `targetType` | `Integer` | Target user type: `1`=tenant, `2`=landlord |
| `title` | `String` | Message title |
| `content` | `String` | Message content |
| `dataId` | `Long` | Associated data ID |
| `userId` | `Long` | Target user ID |
| `senderId` | `Long` | Sender ID |
| `readStatus` | `Integer` | Read status |
| `seeHouseStatus` | `Integer` | Viewing request status |

---

### 9.2 Update Read Status

- **URL**: `PUT /notice/message`
- **Description**: Mark a notice message as read
- **Query Parameters**:

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `id` | `Long` | Yes | - | Message ID |
| `readStatus` | `Integer` | No | `1` | Read status |

- **Response**: `RestResponse<Void>`

---

### 9.3 Update See House Status

- **URL**: `PUT /notice/message/updateStatus`
- **Description**: Update viewing status on a notice message
- **Query Parameters**:

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | `Long` | Yes | Message ID |
| `seeHouseStatus` | `Integer` | Yes | New viewing status |

- **Response**: `RestResponse<Void>`

---

## 10. Identity Request Module

Base path: `/identity`

### 10.1 Submit Identity Request

- **URL**: `POST /identity`
- **Description**: Submit an identity verification request
- **Request Body** (`IdentityRequestEo`): Identity verification data (validated)
- **Response**: `RestResponse<Long>` (returns new request ID)

---

### 10.2 Update Identity Status

- **URL**: `PUT /identity/updateStatus`
- **Description**: Update identity verification status
- **Query Parameters**:

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | `Long` | Yes | Request ID |
| `identityStatus` | `Integer` | Yes | New identity status |

- **Response**: `RestResponse<Void>`

---

### 10.3 Identity Request List

- **URL**: `GET /identity/list`
- **Description**: Query identity requests with pagination
- **Query Parameters**:

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `pageNum` | `Integer` | No | `1` | Page number |
| `pageSize` | `Integer` | No | `10` | Page size |
| *(IdentityRequestEo fields)* | - | No | - | Additional filter criteria |

- **Response**: `RestResponse<TableResult>`

---

## 11. Search Module

Base path: `/search`

### 11.1 Search Houses

- **URL**: `GET /search`
- **Description**: Search house listings by keyword via Elasticsearch (supports CORS)
- **Query Parameters**:

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `keyword` | `String` | Yes | - | Search keyword |
| `page` | `Integer` | No | `1` | Page number (max 100) |

- **Response**: `SearchResult`

| Field | Type | Description |
|-------|------|-------------|
| `totalPage` | `Integer` | Total pages |
| `list` | `List<HouseData>` | Search results |
| `hotWord` | `Set<String>` | Hot search words (when totalPage <= 1) |

---

### 11.2 Search (POST)

- **URL**: `POST /search/get`
- **Description**: Search endpoint (POST variant)
- **Response**: `RestResponse<Void>`

---

## 12. Picture Upload Module

Base path: `/pic/upload`

### 12.1 Upload Single Image

- **URL**: `POST /pic/upload`
- **Description**: Upload a single image file
- **Content-Type**: `multipart/form-data`
- **Request Parameters**:

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `file` | `MultipartFile` | Yes | Image file to upload |

- **Response**: `PicUploadResult`

---

### 12.2 Batch Upload Images

- **URL**: `POST /pic/upload/batch`
- **Description**: Upload multiple images (not fully implemented)
- **Content-Type**: `multipart/form-data`
- **Request Parameters**:

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `uploadFiles` | `List<UploadFileDto>` | Yes | List of files to upload |

- **Response**: `PicUploadResult`

---

## 13. Menu Module

Base path: `/menu`

### 13.1 Get Menu Data

- **URL**: `GET /menu/getMenuData`
- **Description**: Get navigation menu configuration data
- **Response**: `RestResponse` containing the menu JSON structure

---

## 14. GraphQL Module

Base path: `/graphql`

### 14.1 GraphQL Query (GET)

- **URL**: `GET /graphql`
- **Description**: Execute a GraphQL query via GET (supports CORS)
- **Query Parameters**:

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `query` | `String` | Yes | GraphQL query string |
| `variables` | `String` | No | JSON-encoded variables |
| `operationName` | `String` | No | Operation name |

- **Response**: `Map<String, Object>` (GraphQL execution result)

---

### 14.2 GraphQL Query (POST)

- **URL**: `POST /graphql`
- **Description**: Execute a GraphQL query via POST (supports CORS)
- **Request Body**:

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `query` | `String` | Yes | GraphQL query string |
| `variables` | `Map` | No | Variables map |
| `operationName` | `String` | No | Operation name |

- **Response**: `Map<String, Object>` (GraphQL execution result)

---

## 15. IM (Instant Messaging) Module

### 15.1 Query Message List

- **URL**: `GET /message`
- **Description**: Retrieve chat message list between two users (supports CORS)
- **Query Parameters**:

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `fromId` | `Long` | Yes | - | Sender user ID |
| `toId` | `Long` | Yes | - | Receiver user ID |
| `page` | `Integer` | No | `1` | Page number |
| `rows` | `Integer` | No | `10` | Rows per page |

- **Response**: `List<Message>`

**Message fields**:

| Field | Type | Description |
|-------|------|-------------|
| `id` | `ObjectId` | Message ID |
| `msg` | `String` | Message content |
| `status` | `Integer` | Status: `1`=unread, `2`=read |
| `sendDate` | `Date` | Send time |
| `readDate` | `Date` | Read time |
| `from` | `User` | Sender user info |
| `to` | `User` | Receiver user info |

---

### 15.2 Query User Session List

- **URL**: `GET /user/sessionList`
- **Description**: Get chat session list for a user (supports CORS)
- **Query Parameters**:

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `fromId` | `Long` | Yes | User ID |

- **Response**: `RestResponse<List<SessionEo>>`

**SessionEo fields**:

| Field | Type | Description |
|-------|------|-------------|
| `id` | `String` | Session ID |
| `userId` | `Long` | User ID |
| `userName` | `String` | Username |
| `targetId` | `Long` | Target user ID |
| `targetName` | `String` | Target user name |
| `beginTime` | `Date` | Session creation time |
| `startTime` | `Date` | Session start time |
| `avatar` | `String` | Target user avatar |
| `mobile` | `String` | Target phone number |
| `isClose` | `Integer` | Closed status: `0`=open, `1`=closed |
| `lastMessage` | `String` | Last message content |
| `lastMessageTime` | `Date` | Last message time |

---

### 15.3 Add Session

- **URL**: `POST /user/addSession`
- **Description**: Create a new chat session (supports CORS)
- **Request Body** (`SessionEo`):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `userId` | `Long` | Yes | User ID |
| `userName` | `String` | No | Username |
| `targetId` | `Long` | Yes | Target user ID |
| `targetName` | `String` | No | Target user name |
| `avatar` | `String` | No | Target avatar URL |
| `mobile` | `String` | No | Target phone number |

- **Response**: `RestResponse`

---

### 15.4 WebSocket Endpoint

- **URL**: `ws://{host}/ws/{uid}`
- **Description**: WebSocket endpoint for real-time messaging
- **Path Parameters**:

| Parameter | Type | Description |
|-----------|------|-------------|
| `uid` | `String` | User ID for the WebSocket connection |

- **Message Format** (JSON):

| Field | Type | Description |
|-------|------|-------------|
| `msg` | `String` | Message content |
| `from` | `Object` | Sender user info (contains `id`) |
| `to` | `Object` | Receiver user info (contains `id`) |

---

## Enum Reference

### Rental Method (`rentMethod`)
| Value | Description |
|-------|-------------|
| `1` | Whole unit |
| `2` | Shared |

### Viewing Time (`time`)
| Value | Description |
|-------|-------------|
| `1` | Morning |
| `2` | Noon |
| `3` | Afternoon |
| `4` | Evening |
| `5` | All day |

### Check Status (`checkStatus`)
| Value | Description |
|-------|-------------|
| `0` | Pending review |
| `1` | Approved |
| `2` | Rejected |

### Rent Status (`rentStatus`)
| Value | Description |
|-------|-------------|
| `0` | Available |
| `1` | Rented |
| `2` | Expired |

### Decoration (`decoration`)
| Value | Description |
|-------|-------------|
| `1` | Premium |
| `2` | Simple |
| `3` | Unfurnished |

### Contract Status (`contractStatus`)
| Value | Description |
|-------|-------------|
| `0` | Pending signature |
| `1` | Active |
| `2` | Ended |
| `3` | Overdue |

### See House Status (`seeHouseStatus`)
| Value | Description |
|-------|-------------|
| `0` | Pending confirmation |
| `1` | Confirmed / awaiting visit |
| `2` | Cancelled |
| `3` | Completed |

### Rent Out Order Status (`status`)
| Value | Description |
|-------|-------------|
| `0` | Created, pending landlord approval |
| `1` | Pending payment |
| `2` | Paid |

### Notice Target Type (`targetType`)
| Value | Description |
|-------|-------------|
| `1` | Tenant |
| `2` | Landlord |

### Message Status (IM)
| Value | Description |
|-------|-------------|
| `1` | Unread |
| `2` | Read |
