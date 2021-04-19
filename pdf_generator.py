from fpdf import FPDF

pdf = FPDF(format='letter', unit='in')

# Add a page
pdf.add_page()

# Effective page width, or just epw
epw = pdf.w - 2 * pdf.l_margin
pdf.set_font("Arial", size=15)
th = pdf.font_size
col_width = epw / 4


def create_pdf(res_list):
    pdf.cell(col_width, th, str("name"), border=1)
    pdf.cell(col_width, th, str("total_votes"), border=1)
    pdf.ln(th)
    for row in res_list:
        pdf.cell(col_width, th, str(row["name"]), border=1)
        pdf.cell(col_width, th, str(row["total_votes"]), border=1)
        pdf.ln(th)

    pdf.output('static/table_results.pdf', 'F')
