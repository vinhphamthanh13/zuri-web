import { formatCurrency } from 'utils/string';

export const generateEmailContent = contractInfo => {
  const gender = contractInfo.gender === 'MALE' ? 'Nam' : 'Nữ';

  return (
    '<div>' +
    '<p class="MsoNormal"><span lang="EN-AU" style="font-size:12.0pt;font-family:Calibri,sans-serif">Dear [SA],<o:p></o:p></span></p>' +
    '<p class="MsoNormal"><span lang="EN-AU" style="font-size:12.0pt;font-family:Calibri,sans-serif">SA hỗ trợ hoàn tất hồ sơ vay tiền mặt và ký hợp đồng cho khách hàng theo thông tin bên dưới:<o:p></o:p></span></p>' +
    `<table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse">
        <tbody>
            <tr>
                <td style="border:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Số hợp đồng <o:p></o:p></p>
                </td>
                <td style="border:solid black 1.0pt;border-left:none;padding:3.75pt 3.75pt 3.75pt 3.75pt"><p class="MsoNormal">
                    <b><span style="color:red">
                      ${contractInfo.contractNumber}
                    </span></b><o:p></o:p></p>
                </td>
            </tr>
            <tr>
                <td style="border:solid black 1.0pt;border-top:none;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Khách hàng<o:p></o:p></p>
                </td>
                <td style="border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">
                        ${contractInfo.lastName} 
                        ${contractInfo.middleName} 
                        ${contractInfo.firstName}
                    <o:p></o:p></p>
                </td>
            </tr>
            <tr>
                <td style="border:solid black 1.0pt;border-top:none;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Giới tính <o:p></o:p></p>
                </td>
                <td style="border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">${gender}<o:p></o:p></p>
                </td>
            </tr>
            <tr>
                <td style="border:solid black 1.0pt;border-top:none;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Chứng minh nhân dân <o:p></o:p></p>
                </td>
                <td style="border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">
                      ${contractInfo.socialId}
                    <o:p></o:p></p>
                </td>
            </tr>
            <tr>
                <td style="border:solid black 1.0pt;border-top:none;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Số điện thoại<o:p></o:p></p>
                </td>
                <td style="border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">
                      ${contractInfo.phoneNumber}
                    <o:p></o:p></p>
                </td>
            </tr>
            <tr>
                <td style="border:solid black 1.0pt;border-top:none;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Địa điểm liên hệ<o:p></o:p></p>
                </td>
                <td style="border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">${contractInfo.address}<o:p></o:p></p>
                </td>
            </tr>
            <tr>
                <td style="border:solid black 1.0pt;border-top:none;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Giá trị khoản vay<o:p></o:p></p>
                </td>
                <td style="border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">${formatCurrency(
                      contractInfo.creditAmount * 1000000,
                    )}<o:p></o:p></p>
                </td>
            </tr>
            <tr>
                <td style="border:solid black 1.0pt;border-top:none;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Kỳ hạn vay<o:p></o:p></p>
                </td>
                <td style="border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">${contractInfo.tenor}<o:p></o:p></p>
                </td>
            </tr>
            <tr>
                <td style="border:solid black 1.0pt;border-top:none;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Số tiền góp hàng tháng</p>
                </td>
                <td style="border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">${formatCurrency(
                      contractInfo.monthlyPayment,
                    )}</p>
                </td>
            </tr>
            <tr>
                <td style="border:solid black 1.0pt;border-top:none;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Bảo hiểm<o:p></o:p></p>
                </td>
                <td style="border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">${
                      contractInfo.includeInsurance ? 'Có' : 'Không'
                    }<o:p></o:p></p>
                </td>
            </tr>
            <tr>
                <td style="border:solid black 1.0pt;border-top:none;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">Thời gian hẹn<o:p></o:p></p>
                </td>
                <td style="border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:3.75pt 3.75pt 3.75pt 3.75pt">
                    <p class="MsoNormal">${
                      contractInfo.appointmentTime
                    }<o:p></o:p></p>
                </td>
            </tr>
        </tbody>
    </table>` +
    '<p class="MsoNormal"><span lang="EN-AU" style="font-size:16.0pt;color:red;font-family:Calibri,sans-serif"><b>CÁC LƯU Ý TRƯỚC KHI LÀM HỢP ĐỒNG ACL O2O</b><o:p></o:p></span></p>' +
    '<p><b><span lang="EN-AU" style="font-size:11.0pt;font-family:Calibri,sans-serif">1. SA cần so sách thông tin application trên BSL (1BOD) với giấy tờ tùy thân của KH, bao gồm:<o:p></o:p></span></b></p>' +
    '<p style="margin-left:36.0pt;text-indent:-18.0pt;mso-list:l0 level1 lfo6"><!--[if !supportLists]--><span lang="EN-AU" style="font-size:11.0pt;font-family:Wingdings"><span style="mso-list:Ignore">ü<span style="font:7.0pt Calibri,sans-serif"> </span></span></span><!--[endif]--><span lang="EN-AU" style="font-size:11.0pt;font-family:Calibri,sans-serif">Họ và tên, CMND, ngày sinh, khoản vay khác,… và kiểm tra kỹ trước khi hỗ trợ hoàn tất 2BOD <o:p></o:p></span></p>' +
    '<p style="margin-left:36.0pt;text-indent:-18.0pt;mso-list:l0 level1 lfo6"><!--[if !supportLists]--><span lang="EN-AU" style="font-size:11.0pt;font-family:Wingdings"><span style="mso-list:Ignore">ü<span style="font:7.0pt Calibri,sans-serif"> </span></span></span><!--[endif]--><span lang="EN-AU" style="font-size:11.0pt;font-family:Calibri,sans-serif">Cần kiểm tra lại tất cả các thông tin, giấy tờ tùy thân theo quy định của công ty trước khi nộp hồ sơ thẩm định vì mọi trường hợp nhập sai sẽ dẫn đến HĐ có thể bị từ chối (reject) hoặc bị đánh lỗi. <o:p></o:p></span></p>' +
    '<p style="margin-left:36.0pt;text-indent:-18.0pt;mso-list:l0 level1 lfo6"><!--[if !supportLists]--><span lang="EN-AU" style="font-size:11.0pt;font-family:Wingdings"><span style="mso-list:Ignore">ü<span style="font:7.0pt Calibri,sans-serif"> </span></span></span><!--[endif]--><span lang="EN-AU" style="font-size:11.0pt;font-family:Calibri,sans-serif">Trong trường hợp hình khách hàng tự chụp/upload không giống hoặc mờ - 1BOD. SA có thể bỏ qua và chụp lại hình, upload lại ở 2BOD hoặc sau khi hợp đồng được duyệt nhưng phải trước khi ký hợp đồng. <o:p></o:p></span></p>' +
    '<p><b><span lang="EN-AU" style="font-size:11.0pt;font-family:Calibri,sans-serif">2. Trường hợp khách hàng nhập thông tin sai sót trên Landing page - 1BOD: <o:p></o:p></span></b></p>' +
    '<p style="margin-left:36.0pt"><span lang="EN-AU" style="font-size:11.0pt;font-family:Calibri,sans-serif">SA hỗ trợ hủy hợp đồng trên BSL và có thể hướng dẫn khách hàng nhập lại thông tin chính xác trên Landing page. <o:p></o:p></span></p>' +
    '<p><b><span lang="EN-AU" style="font-size:11.0pt;font-family:Calibri,sans-serif">3. Vui lòng liên hệ để làm hồ sơ cho khách hàng trong vòng 2 giờ kể từ lúc nhận Email:<o:p></o:p></span></b></p>' +
    '<p style="text-indent:36.0pt"><span lang="EN-AU" style="font-size:11.0pt;font-family:Calibri,sans-serif">Tính từ lúc nhận email này, trường hợp không làm được hợp đồng hoặc bất kỳ thay đổi nào vui lòng reply all hoặc gởi email đến: <a href="mailto:aclo2o_support@homecredit.vn">aclo2o_support@homecredit.vn</a> đễ được hỗ trợ.<o:p></o:p></span></p>' +
    '<p><b><span lang="EN-AU" style="font-size:11.0pt;font-family:Calibri,sans-serif">4. Trường hợp không thể hỗ trợ: <o:p></o:p></span></b></p>' +
    '<p style="text-indent:36.0pt"><span lang="EN-AU" style="font-size:11.0pt;font-family:Calibri,sans-serif">Trường hợp không liên hệ được khách hàng hoặc đang làm ở SHOP không cho phép làm HĐ ACL vui lòng gởi email đến: <a href="mailto:aclo2o_support@homecredit.vn">aclo2o_support@homecredit.vn</a><o:p></o:p></span></p>' +
    '</div>'
  );
};
