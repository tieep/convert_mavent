/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package API;

/**
 *
 * @author hp
 */
import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.ServerSocket;
import dao.NhaCungCapDAO;
import dto.NhaCungCapDTO;
import static fi.iki.elonen.NanoHTTPD.SOCKET_READ_TIMEOUT;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;
import java.util.HashMap;
import java.util.Map;

public class API_Server extends NanoHTTPD {

    public API_Server(int port) throws IOException {
        super(port);

        // Kiểm tra xem cổng đã được sử dụng chưa
        if (isPortInUse(port)) {

        } else {
            start(SOCKET_READ_TIMEOUT, false); // Khởi chạy server
            System.out.println("API server started on port " + port);
        }

    }

    // Kiểm tra cổng có đang sử dụng không
    private boolean isPortInUse(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            return false; // Cổng chưa bị chiếm dụng
        } catch (IOException e) {
            return true; // Cổng đã bị chiếm dụng
        }
    }

    @Override
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {
        String uri = session.getUri();
        String method = session.getMethod().name();
        ObjectMapper objectMapper = new ObjectMapper();

        switch (uri) {
            case "/api/get-suppliers":
                if ("GET".equals(method)) {

                    return handleGetNhaCungCap(session, objectMapper);
                }
                break;

            case "/api/create-suppliers":
                if ("POST".equals(method)) {
                    return handleCreateNhaCungCap(session, objectMapper);
                }
                break;

            case "/api/update-suppliers":
                if ("PUT".equals(method)) {

                    return handleUpdateNhaCungCap(session, objectMapper);
                }
                break;
            case "/api/delete-suppliers":
                if ("DELETE".equals(method)) {
                    return handleDeleteNhaCungCap(session, objectMapper);
                }
                break;
            default:
                return newFixedLengthResponse(
                        NanoHTTPD.Response.Status.NOT_FOUND,
                        "application/json",
                        "{\"status\": 404, \"message\": \"Không tìm thấy API\"}"
                );

        }
        return newFixedLengthResponse(
                NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
                "application/json",
                "{\"status\": 405, \"message\": \"Reponse cho phương thức chưa được thiết lập\"}"
        );

    }
    Map<String, Object> response = new HashMap<>();

    private NanoHTTPD.Response handleGetNhaCungCap(NanoHTTPD.IHTTPSession session, ObjectMapper objectMapper) {
        try {
            NhaCungCapDAO ncc = new NhaCungCapDAO();
            ArrayList<NhaCungCapDTO> nccList = ncc.list();
            // Lấy tham số từ query

            String idNhaCungCap = session.getParms().get("idNhaCungCap");

            // Lọc danh sách theo điều kiện
            if (idNhaCungCap != null && !idNhaCungCap.isEmpty()) {
                nccList.removeIf(nccItem -> !nccItem.getIdNhaCungCap().equals(idNhaCungCap));
            }
            HashMap<String, Object> response = new HashMap<>();
            response.put("data", nccList);
            response.put("status", 200);
            if (nccList.isEmpty()) {
                response.put("message", "Không có dữ liệu phù hợp");
            } else {
                response.put("message", "Lấy danh sách nhà cung cấp thành công");
            }
            // Chuyển danh sách thành JSON
            String jsonResponse = objectMapper.writeValueAsString(response);
            return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            HashMap<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 400);
            errorResponse.put("message", "Bad request");
            try {
                String errorJson = objectMapper.writeValueAsString(errorResponse);
                return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json", errorJson);
            } catch (IOException ioException) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "text/plain", "500 Internal Server Error");
            }
        }
    }
// Xử lý thêm nhà cung cấp

    private NanoHTTPD.Response handleCreateNhaCungCap(NanoHTTPD.IHTTPSession session, ObjectMapper objectMapper) {
        try {
            // Đọc dữ liệu từ body
            Map<String, String> body = new HashMap<>();
            session.parseBody(body);
            String payload = body.get("postData");
            NhaCungCapDTO newSupplier = objectMapper.readValue(payload, NhaCungCapDTO.class);

            // Kiểm tra dữ liệu đầu vào
            if (newSupplier.getTenNhaCungCap() == null || newSupplier.getTenNhaCungCap().isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json", "{\"status\": 400, \"message\": \"Tên nhà cung cấp không được để trống\"}");
            }
            if (newSupplier.getSdt() == null || newSupplier.getSdt().isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Số điện thoại không được để trống\"}");
            }

            // Kiểm tra nếu số điện thoại chứa ký tự không phải số
            if (!newSupplier.getSdt().matches("\\d+")) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Số điện thoại không hợp lệ\"}");
            }// thêm kiểm tra điều kiện nếu cần, tại tui thấy cần chụp có 1 trường hợp lỗi thôi nên bắt có 1 lỗi, nếu cần thiết thì làm kĩ hơn
            NhaCungCapDAO nccDAO = new NhaCungCapDAO();
            nccDAO.create_ncc(newSupplier);
            return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", "{\"status\": 200, \"message\": \"Thêm nhà cung cấp thành công\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json", "{\"status\": 500, \"message\": \"Đã xảy ra lỗi\"}");
        }
    }

//    private NanoHTTPD.Response handleUpdateNhaCungCap(NanoHTTPD.IHTTPSession session, ObjectMapper objectMapper) {
//        try {
//            // Lấy độ dài body từ request header
//            int contentLength = Integer.parseInt(session.getHeaders().getOrDefault("content-length", "0"));
//            if (contentLength <= 0) {
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"Không có dữ liệu\"}");
//            }
//
//            // Đọc body từ InputStream với giới hạn dung lượng
//            byte[] buffer = new byte[contentLength];
//            int read = session.getInputStream().read(buffer, 0, contentLength);
//            if (read <= 0) {
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"Không đọc được dữ liệu\"}");
//            }
//
//            // Chuyển đổi byte array thành string (payload)
//            String payload = new String(buffer, 0, read);
//            System.out.println("Payload: " + payload);
//
//            // Parse payload từ JSON thành Map
//            Map<String, String> requestData = objectMapper.readValue(payload, HashMap.class);
//
//            // Lấy dữ liệu từ payload
//            String idNhaCungCap = requestData.get("idNhaCungCap");
//            String diaChiMoi = requestData.get("diaChi");
//
//            // Kiểm tra dữ liệu đầu vào
//            if (idNhaCungCap == null || idNhaCungCap.isEmpty()) {
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"ID nhà cung cấp không được để trống\"}");
//            }
//            if (diaChiMoi == null || diaChiMoi.isEmpty()) {
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"Địa chỉ mới không được để trống\"}");
//            }
//
//            // Loại bỏ khoảng trắng đầu và cuối của địa chỉ
//            diaChiMoi = diaChiMoi.trim();
//
//            // Kiểm tra độ dài địa chỉ
//            if (diaChiMoi.length() < 10 || diaChiMoi.length() > 60) {
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"Địa chỉ mới phải có độ dài từ 10 đến 60 ký tự\"}");
//            }
//
//            // Kiểm tra tính hợp lệ của địa chỉ (cho phép ký tự tiếng Việt có dấu)
//            if (!diaChiMoi.matches("^[a-zA-Z0-9À-ỹ,\\-\\/\\s]+$")) {
//                System.out.println("Địa chỉ không hợp lệ: " + diaChiMoi);
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"Địa chỉ mới không hợp lệ.\"}");
//            }
//
//            // Cập nhật thông tin địa chỉ trong cơ sở dữ liệu
//            NhaCungCapDAO nccDAO = new NhaCungCapDAO();
//            nccDAO.updateAddress(idNhaCungCap, diaChiMoi);
//
//            // Phản hồi thành công
//            return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json",
//                    "{\"status\": 200, \"message\": \"Cập nhật địa chỉ thành công\"}");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json",
//                    "{\"status\": 500, \"message\": \"Đã xảy ra lỗi\"}");
//        }
//    }
    private NanoHTTPD.Response handleUpdateNhaCungCap(NanoHTTPD.IHTTPSession session, ObjectMapper objectMapper) {
        try {
            // Lấy idNhaCungCap từ query parameters
            Map<String, String> params = session.getParms();
            String idNhaCungCap = params.get("idNhaCungCap");

            // Kiểm tra idNhaCungCap
            if (idNhaCungCap == null || idNhaCungCap.isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"ID nhà cung cấp không được để trống\"}");
            }

            // Lấy độ dài body từ request header
            int contentLength = Integer.parseInt(session.getHeaders().getOrDefault("content-length", "0"));
            if (contentLength <= 0) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Không có dữ liệu\"}");
            }

            // Đọc body từ InputStream với giới hạn dung lượng
            byte[] buffer = new byte[contentLength];
            int read = session.getInputStream().read(buffer, 0, contentLength);
            if (read <= 0) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Không đọc được dữ liệu\"}");
            }

            // Chuyển đổi byte array thành string (payload)
            String payload = new String(buffer, 0, read);
            System.out.println("Payload: " + payload);

            // Parse payload từ JSON để lấy diaChi
            Map<String, String> requestData = objectMapper.readValue(payload, HashMap.class);
            String diaChiMoi = requestData.get("diaChi");

            // Kiểm tra diaChiMoi
            if (diaChiMoi == null || diaChiMoi.isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ mới không được để trống\"}");
            }

            // Loại bỏ khoảng trắng đầu và cuối của địa chỉ
            diaChiMoi = diaChiMoi.trim();

            // Kiểm tra độ dài địa chỉ
            if (diaChiMoi.length() < 10 || diaChiMoi.length() > 60) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ mới phải có độ dài từ 10 đến 60 ký tự\"}");
            }

            // Kiểm tra tính hợp lệ của địa chỉ (cho phép ký tự tiếng Việt có dấu)
            if (!diaChiMoi.matches("^[a-zA-Z0-9À-ỹ,\\-\\/\\s]+$")) {
                System.out.println("Địa chỉ không hợp lệ: " + diaChiMoi);
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ mới không hợp lệ.\"}");
            }

            // Cập nhật thông tin địa chỉ trong cơ sở dữ liệu
            NhaCungCapDAO nccDAO = new NhaCungCapDAO();
            nccDAO.updateAddress(idNhaCungCap, diaChiMoi);

            // Phản hồi thành công
            return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json",
                    "{\"status\": 200, \"message\": \"Cập nhật địa chỉ thành công\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json",
                    "{\"status\": 500, \"message\": \"Đã xảy ra lỗi\"}");
        }
    }

// Xử lý xóa nhà cung cấp
    private NanoHTTPD.Response handleDeleteNhaCungCap(NanoHTTPD.IHTTPSession session, ObjectMapper objectMapper) {
        try {
            // Lấy tham số id từ query
            String idNhaCungCap = session.getParms().get("id");
            if (idNhaCungCap == null || idNhaCungCap.isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"ID nhà cung cấp không được để trống\"}");
            }

            NhaCungCapDAO nccDAO = new NhaCungCapDAO();

            try {
                // Gọi hàm xóa và xử lý theo kết quả trả về
                String result = nccDAO.deleteDB_by_tiep(idNhaCungCap);

                if ("deleted".equals(result)) {
                    // Nếu xóa hoàn toàn
                    return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json",
                            "{\"status\": 200, \"message\": \"Xóa nhà cung cấp thành công\"}");
                } else if ("updated".equals(result)) {
                    // Nếu chỉ cập nhật trạng thái
                    String message = "Xóa nhà cung cấp thành công";
                    return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json",
                            "{\"status\": 200, \"message\": \"" + message + "\"}");
                } else {
                    // Trường hợp không mong muốn (lý thuyết không xảy ra)
                    return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json",
                            "{\"status\": 500, \"message\": \"Đã xảy ra lỗi không xác định\"}");
                }
            } catch (IllegalArgumentException e) {
                // Xử lý lỗi ID không tồn tại
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"" + e.getMessage() + "\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json",
                    "{\"status\": 500, \"message\": \"Đã xảy ra lỗi\"}");
        }
    }

}
