/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package API;

/**
 *
 * @author hp
 */
import com.fasterxml.jackson.core.JsonGenerator;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
        objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        switch (uri) {
            case "/api/find-suppliers":
                if ("GET".equals(method)) {

                    return handleFindNhaCungCap(session, objectMapper);
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

    private NanoHTTPD.Response handleFindNhaCungCap(NanoHTTPD.IHTTPSession session, ObjectMapper objectMapper) {
        try {
            NhaCungCapDAO ncc = new NhaCungCapDAO();
            ArrayList<NhaCungCapDTO> nccList = ncc.list();

            // Lấy tham số từ query
            Map<String, List<String>> params = session.getParameters();

            // Danh sách các key hợp lệ
            ArrayList<String> validKeys = new ArrayList<>();
            validKeys.add("type");
            validKeys.add("find");

            // Kiểm tra nếu có tham số không hợp lệ
            for (String key : params.keySet()) {
                if (!validKeys.contains(key)) {
                    HashMap<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("status", 400);
                    errorResponse.put("message", "Tham số không hợp lệ : " + key);
                    String errorJson = objectMapper.writeValueAsString(errorResponse);
                    return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json", errorJson);
                }
            }

            // Lấy giá trị của 'type' và 'find'
            List<String> searchTypeList = params.get(validKeys.get(0));
            String searchType = (searchTypeList != null && !searchTypeList.isEmpty()) ? searchTypeList.get(0) : null;

            List<String> contentSearchList = params.get(validKeys.get(1));
            String contentSearch = (contentSearchList != null && !contentSearchList.isEmpty()) ? contentSearchList.get(0) : null;

            int type = 0; // Tìm kiếm tất cả nếu không có 'type'
            if (searchType != null) {
                type = Integer.parseInt(searchType);
            }

            // Kiểm tra nếu type không hợp lệ
            if (type > 4) {
                HashMap<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", 400);
                errorResponse.put("message", "Danh mục tìm kiếm không hợp lệ. Giá trị 'type' phải nằm trong khoảng từ 0 đến 4.");
                String errorJson = objectMapper.writeValueAsString(errorResponse);
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json", errorJson);
            }

            // Lọc danh sách theo điều kiện
            ArrayList<NhaCungCapDTO> result = new ArrayList<>();
            if (contentSearch != null && !contentSearch.isEmpty()) {
                result = new ArrayList<>(search(nccList, contentSearch, type));
            } else {
                result = new ArrayList<>(nccList);
            }

            // Tạo phản hồi JSON
            HashMap<String, Object> response = new HashMap<>();
            response.put("data", result);

            if (result.isEmpty()) {
                response.put("status", 404);
                response.put("message", "Không có dữ liệu phù hợp");
                String jsonResponse = objectMapper.writeValueAsString(response);
                return newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, "application/json", jsonResponse);
            } else {
                response.put("status", 200);
                response.put("message", "Lấy danh sách nhà cung cấp thành công");
                String jsonResponse = objectMapper.writeValueAsString(response);
                return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", jsonResponse);
            }

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

    public LinkedHashSet<NhaCungCapDTO> searchId(ArrayList<NhaCungCapDTO> data, String content) {
        LinkedHashSet<NhaCungCapDTO> setNCC = new LinkedHashSet<>(data);
        return setNCC.stream()
                .filter(ncc -> ncc.getIdNhaCungCap().contains(content))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LinkedHashSet<NhaCungCapDTO> searchName(ArrayList<NhaCungCapDTO> data, String content) {
        LinkedHashSet<NhaCungCapDTO> setNCC = new LinkedHashSet<>(data);
        return setNCC.stream()
                .filter(ncc -> ncc.getTenNhaCungCap().toLowerCase().contains(content.toLowerCase()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LinkedHashSet<NhaCungCapDTO> searchDiaChi(ArrayList<NhaCungCapDTO> data, String content) {
        LinkedHashSet<NhaCungCapDTO> setNCC = new LinkedHashSet<>(data);
        return setNCC.stream()
                .filter(ncc -> ncc.getDiachi().toLowerCase().contains(content.toLowerCase()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LinkedHashSet<NhaCungCapDTO> searchPhone(ArrayList<NhaCungCapDTO> data, String content) {
        LinkedHashSet<NhaCungCapDTO> setNCC = new LinkedHashSet<>(data);
        return setNCC.stream()
                .filter(ncc -> ncc.getSdt().contains(content))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LinkedHashSet<NhaCungCapDTO> search(ArrayList<NhaCungCapDTO> data, String content, int type) {
        if (type == 0) {
            LinkedHashSet<NhaCungCapDTO> setId = searchId(data, content);
            LinkedHashSet<NhaCungCapDTO> setName = searchName(data, content);
            LinkedHashSet<NhaCungCapDTO> setDiaChi = searchDiaChi(data, content);
            LinkedHashSet<NhaCungCapDTO> setPhone = searchPhone(data, content);
            LinkedHashSet<NhaCungCapDTO> setAll = Stream.of(setId, setName, setPhone, setDiaChi)
                    .flatMap(LinkedHashSet::stream)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            return setAll;
        }

        if (type == 1) {
            return searchId(data, content);
        }
        if (type == 2) {
            return searchName(data, content);
        }
        if (type == 3) {
            return searchDiaChi(data, content);
        }
        if (type == 4) {
            return searchPhone(data, content);
        }
        return new LinkedHashSet<>();
    }
// Xử lý thêm nhà cung cấp

    private NanoHTTPD.Response handleCreateNhaCungCap(NanoHTTPD.IHTTPSession session, ObjectMapper objectMapper) {
        try {
            Map<String, String> body = new HashMap<>();
            session.parseBody(body);
            String payload = body.get("postData");
            System.err.println("payload:" + payload);
            NhaCungCapDTO newSupplier = objectMapper.readValue(payload, NhaCungCapDTO.class);

            // Kiểm tra dữ liệu đầu vào
            if (newSupplier.getTenNhaCungCap() == null || newSupplier.getTenNhaCungCap().isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Tên nhà cung cấp không được để trống\"}");
            }
            if (newSupplier.getTenNhaCungCap().length() < 6) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Tên nhà cung cấp phải từ 6 kí tự trở lên\"}");
            }
            if (newSupplier.getTenNhaCungCap().length() >= 50) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Tên nhà cung cấp phải dưới 50 kí tự\"}");
            }
            // khúc này check kí tự đặc biệt vs chữ số
            if (!newSupplier.getTenNhaCungCap().matches("^[a-zA-Z\\s]+$")) {
                if (!newSupplier.getTenNhaCungCap().matches("^[^\\d]+$")) {
                    return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                            "{\"status\": 400, \"message\": \"Tên nhà cung cấp không được chứa chữ số\"}");
                } else if (!newSupplier.getTenNhaCungCap().matches("^[a-zA-Z\\s]+$")) {
                    return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                            "{\"status\": 400, \"message\": \"Tên nhà cung cấp không được chứa ký tự đặc biệt\"}");
                }
            }

            if (newSupplier.getDiachi() == null || newSupplier.getDiachi().isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ  không được để trống\"}");
            }
            if (newSupplier.getDiachi().length() < 11) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ phải trên 10 kí tự\"}");
            }
            if (newSupplier.getDiachi().length() >= 60) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ nhà cung cấp dưới 60  kí tự\"}");
            }
            String diaChi = newSupplier.getDiachi().trim();
            if (!diaChi.matches("^[a-zA-Z0-9À-ỹ,\\-\\/\\s]+$")) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ không được chứa các ký tự đặc biệt\"}");
            }
            if (newSupplier.getSdt() == null || newSupplier.getSdt().isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Số điện thoại không được để trống\"}");
            }

            if (!newSupplier.getSdt().matches("^0\\d{9}$")) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Số điện thoại không hợp lệ. Số điện thoại phải bắt đầu bằng số 0 và theo sau là 9 chữ số\"}");
            }

            NhaCungCapDAO nccDAO = new NhaCungCapDAO();
            if (nccDAO.isPhoneDuplicate(newSupplier.getSdt())) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Số điện thoại đã tồn tại trong hệ thống\"}");
            }

            nccDAO.create_ncc(newSupplier);
            return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json",
                    "{\"status\": 200, \"message\": \"Thêm nhà cung cấp thành công\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json",
                    "{\"status\": 500, \"message\": \"Đã xảy ra lỗi\"}");
        }
    }

//    private NanoHTTPD.Response handleUpdateNhaCungCap(NanoHTTPD.IHTTPSession session, ObjectMapper objectMapper) {
//        try {
//            Map<String, String> params = session.getParms();
//            String idNhaCungCap = params.get("idNhaCungCap");
//
//            if (idNhaCungCap == null || idNhaCungCap.isEmpty()) {
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"ID nhà cung cấp không được để trống\"}");
//            }
//
//            int contentLength = Integer.parseInt(session.getHeaders().getOrDefault("content-length", "0"));
//            if (contentLength <= 0) {
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"Không có dữ liệu\"}");
//            }
//
//            byte[] buffer = new byte[contentLength];
//            int read = session.getInputStream().read(buffer, 0, contentLength);
//            if (read <= 0) {
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"Không đọc được dữ liệu\"}");
//            }
//
//            String payload = new String(buffer, 0, read);
//            System.out.println("Payload: " + payload);
//
//            Map<String, String> requestData = objectMapper.readValue(payload, HashMap.class);
//            String diaChiMoi = requestData.get("diaChi");
//
//            if (diaChiMoi == null || diaChiMoi.isEmpty()) {
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"Địa chỉ mới không được để trống\"}");
//            }
//
//            diaChiMoi = diaChiMoi.trim();
//
//            if (diaChiMoi.length() < 10 || diaChiMoi.length() > 60) {
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"Địa chỉ mới phải có độ dài từ 10 đến 60 ký tự\"}");
//            }
//
//            if (!diaChiMoi.matches("^[a-zA-Z0-9À-ỹ,\\-\\/\\s]+$")) {
//                System.out.println("Địa chỉ không hợp lệ: " + diaChiMoi);
//                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
//                        "{\"status\": 400, \"message\": \"Địa chỉ mới không hợp lệ.\"}");
//            }
//
//            NhaCungCapDAO nccDAO = new NhaCungCapDAO();
//            nccDAO.updateAddress(idNhaCungCap, diaChiMoi);
//
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
            Map<String, String> params = session.getParms();
            String idNhaCungCap = params.get("idNhaCungCap");

            int contentLength = Integer.parseInt(session.getHeaders().getOrDefault("content-length", "0"));
            if (contentLength <= 0) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Không có dữ liệu\"}");
            }

            byte[] buffer = new byte[contentLength];
            int read = session.getInputStream().read(buffer, 0, contentLength);
            if (read <= 0) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Không đọc được dữ liệu\"}");
            }

            String payload = new String(buffer, 0, read);
            System.out.println("Payload: " + payload);

            Map<String, String> requestData = objectMapper.readValue(payload, HashMap.class);

            // Kiểm tra nếu idNhaCungCap không có trong params thì lấy từ body
            if (idNhaCungCap == null || idNhaCungCap.isEmpty()) {
                idNhaCungCap = requestData.get("idNhaCungCap");
            }

            if (idNhaCungCap == null || idNhaCungCap.isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"ID nhà cung cấp không được để trống\"}");
            }
            if (!idNhaCungCap.matches("^CC\\d+$")) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"ID nhà cung cấp không đúng định dạng. Định dạng hợp lệ là 'CC' theo sau bởi các chữ số\"}");
            }
            NhaCungCapDAO nccDAO = new NhaCungCapDAO();
            if (!nccDAO.checkExists(idNhaCungCap)) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, "application/json",
                        "{\"status\": 404, \"message\": \"Mã nhà cung cấp không tồn tại trong hệ thống\"}");
            }
            String diaChiMoi = requestData.get("diaChi");
            if (diaChiMoi == null || diaChiMoi.isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ mới không được để trống\"}");
            }

            diaChiMoi = diaChiMoi.trim();

            if (diaChiMoi.length() < 11) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ nhà cung cấp phải trên 10 kí tự\"}");
            }
            if (diaChiMoi.length() > 60) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ nhà cung cấp dưới 60  kí tự\"}");
            }
            if (!diaChiMoi.matches("^[a-zA-Z0-9À-ỹ,\\-\\/\\s]+$")) {
                System.out.println("Địa chỉ không hợp lệ: " + diaChiMoi);
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"Địa chỉ mới không hợp lệ.\"}");
            }
            nccDAO.updateAddress(idNhaCungCap, diaChiMoi);

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
            String idNhaCungCap = session.getParms().get("idNhaCungCap");
            if (idNhaCungCap == null || idNhaCungCap.isEmpty()) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"ID nhà cung cấp không được để trống\"}");
            }

            // Kiểm tra định dạng ID nhà cung cấp
            if (!idNhaCungCap.matches("^CC\\d{3}$")) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "application/json",
                        "{\"status\": 400, \"message\": \"ID nhà cung cấp không hợp lệ.\"}");
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
                    return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json",
                            "{\"status\": 200, \"message\": \"Xóa nhà cung cấp thành công\"}");
                } else {
                    // Trường hợp không mong muốn (lý thuyết không xảy ra)
                    return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json",
                            "{\"status\": 500, \"message\": \"Đã xảy ra lỗi không xác định\"}");
                }
            } catch (IllegalArgumentException e) {
                // Trả về lỗi 404 nếu ID không tồn tại
                return newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, "application/json",
                        "{\"status\": 404, \"message\": \"" + e.getMessage() + "\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json",
                    "{\"status\": 500, \"message\": \"Đã xảy ra lỗi\"}");
        }
    }

}
